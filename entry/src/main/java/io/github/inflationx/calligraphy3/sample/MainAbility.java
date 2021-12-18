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

import io.github.inflationx.calligraphy3.ResourceTable;
import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.agp.text.Font;
import ohos.app.Context;
import ohos.app.Environment;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;


public class MainAbility extends FractionAbility {
    private PlaceholderFragment placeholderFragment;
    public static Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initComponents();
    }

    public static Font createFont(Context context, String name) {
        ResourceManager resManager = context.getResourceManager();
        RawFileEntry rawFileEntry = resManager.getRawFileEntry("resources/rawfile/" + name);
        Resource resource = null;
        try {
            resource = rawFileEntry.openRawFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer fileName = new StringBuffer(name);
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString());
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int index;
            byte[] bytes = new byte[1024];
            while ((index = resource.read(bytes)) != -1) {
                outputStream.write(bytes, 0, index);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                resource.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Font.Builder builder = new Font.Builder(file);
        return builder.build();
    }

    private void initComponents() {
        placeholderFragment = new PlaceholderFragment();
        getFractionManager().startFractionScheduler().replace(
                ResourceTable.Id_container, placeholderFragment).submit();
    }
}