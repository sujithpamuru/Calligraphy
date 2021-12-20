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

import static io.github.inflationx.calligraphy3.sample.PlaceholderFragment.DIALOG_BOX_CORNER_RADIUS;
import static io.github.inflationx.calligraphy3.sample.PlaceholderFragment.DIALOG_BOX_WIDTH;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;
import io.github.inflationx.calligraphy3.ResourceTable;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

/**
 * CustomDialog.
 */
public class CustomDialog extends CommonDialog {
    private Text titleText;
    private Text text;
    private Text absoluteText;
    private Component confirmButton;
    private ConfirmListener confirmListener;
    private final Context context;

    /**
     * CustomDialog.
     *
     * @param abilityContext Context
     */

    public CustomDialog(Context abilityContext) {
        super(abilityContext);
        this.context = abilityContext;
        initComponents();
        setCornerRadius(DIALOG_BOX_CORNER_RADIUS);
        setAlignment(TextAlignment.CENTER);
        setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT);
    }

    private void initComponents() {
        Component customComponent = LayoutScatter.getInstance(context)
                .parse(ResourceTable.Layout_custom_dialog_content, null, true);
        titleText = (Text) customComponent.findComponentById(ResourceTable.Id_title_text);
        text = (Text) customComponent.findComponentById(ResourceTable.Id_text);
        absoluteText = (Text) customComponent.findComponentById(ResourceTable.Id_absolute);
        confirmButton = customComponent.findComponentById(ResourceTable.Id_confirm_button);
        super.setContentCustomComponent(customComponent);
        confirm();
    }

    /**
     * set title.
     *
     * @param string String
     */
    public void setTitle(String string) {
        titleText.setText(string);
    }

    /**
     * set text.
     *
     * @param string String
     */
    public void setToText(String string) {
        text.setText(string);
    }

    /**
     * set text.
     *
     * @param string String
     */
    public void setAbsoluteText(String string) {
        absoluteText.setText(string);
    }

    private String getContent() {
        return "";
    }

    private void confirm() {
        confirmButton.setClickedListener(component -> {
            if (confirmListener != null) {
                confirmListener.onConfirmListener(getContent());
            }
        });
    }

    /**
     * setOnConfirmListener.
     *
     * @param confirm ConfirmListener
     */
    public void setOnConfirmListener(ConfirmListener confirm) {
        confirmListener = confirm;
    }
}