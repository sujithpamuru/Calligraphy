/*
 * Copyright(C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on as "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or impiles.
 * See the License for the specific language governing permissions and
 * Limitations under the License.
 */

package io.github.inflationx.calligraphy3.sample;

import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.agp.text.Font;
import ohos.app.Context;
import ohos.app.Environment;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import io.github.inflationx.calligraphy3.ResourceTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * MainAbility.
 */
public class MainAbility extends FractionAbility {
    /**
     * TYPE.
     */
    private static final int HILOG_TYPE = 3;
    /**
     * DOMAIN.
     */
    private static final int HILOG_DOMAIN = 0xD000F00;
    /**
     * LABEL.
     */
    private static final HiLogLabel LABEL = new HiLogLabel(HILOG_TYPE, HILOG_DOMAIN, "Calligraphy");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initComponents();
    }

    /**
     * To set Font.
     *
     * @param context context
     * @param name    name
     * @return font
     */

    public static Font createFont(Context context, String name) {
        ResourceManager resManager = context.getResourceManager();
        RawFileEntry rawFileEntry = resManager.getRawFileEntry("resources/rawfile/" + name);
        Resource resource = null;
        try {
            resource = rawFileEntry.openRawFile();
        } catch (IOException e) {
            HiLog.error(LABEL, e.getMessage());
        }
        StringBuilder fileName = new StringBuilder(name);
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString());
        try (OutputStream outputStream = new FileOutputStream(file)) {
            int index;
            byte[] bytes = new byte[1024];
            while ((index = resource.read(bytes)) != -1) {
                outputStream.write(bytes, 0, index);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            HiLog.error(LABEL, e.getMessage());
        } catch (IOException e) {
            HiLog.error(LABEL, e.getMessage());
        } finally {
            try {
                resource.close();
            } catch (IOException e) {
                HiLog.error(LABEL, e.getMessage());
            }
        }
        Font.Builder builder = new Font.Builder(file);
        return builder.build();
    }

    private void initComponents() {
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        getFractionManager().startFractionScheduler().replace(
                ResourceTable.Id_container, placeholderFragment).submit();
    }
}