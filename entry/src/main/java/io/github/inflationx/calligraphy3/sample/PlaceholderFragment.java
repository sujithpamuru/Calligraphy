package io.github.inflationx.calligraphy3.sample;

import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Checkbox;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import io.github.inflationx.calligraphy3.ResourceTable;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fraction {

    /**
     * DIALOG_BOX_CORNER_RADIUS.
     */
    public static final float DIALOG_BOX_CORNER_RADIUS = 36.0f;
    /**
     * DIALOG_BOX_WIDTH.
     */
    public static final int DIALOG_BOX_WIDTH = 984;
    /**
     * FIRST_FILE_PATH.
     */
    public static final String FIRST_FILE_PATH = "system/fonts/NotoSansSyriacWestern-Regular.ttf";
    /**
     * ROBOTO_BOLD.
     */
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    /**
     * OSWALD_STENCBAB.
     */
    public static final String OSWALD_STENCBAB = "Oswald-Stencbab.ttf";

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        Component component = scatter.parse(ResourceTable.Layout_fragment_main, container, false);
        Button button = (Button) component.findComponentById(ResourceTable.Id_button_bold);
        Context context = getFractionAbility();
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                new ToastDialog(context)
                        .setText("Custom Typeface toast text")
                        .setTransparent(true)
                        .show();
            }
        });
        Text textTwo = (Text) component.findComponentById(ResourceTable.Id_text_two);
        textTwo.setFont(MainAbility.createFont(context, ROBOTO_BOLD));
        Text textThree = (Text) component.findComponentById(ResourceTable.Id_text_three);
        textThree.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textFour = (Text) component.findComponentById(ResourceTable.Id_text_four);
        textFour.setFont(MainAbility.createFont(context, ROBOTO_BOLD));
        Text textSeven = (Text) component.findComponentById(ResourceTable.Id_text_seven);
        textSeven.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textEight = (Text) component.findComponentById(ResourceTable.Id_text_eight);
        textEight.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textNine = (Text) component.findComponentById(ResourceTable.Id_text_nine);
        textNine.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textTen = (Text) component.findComponentById(ResourceTable.Id_text_ten);
        textTen.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textEleven = (Text) component.findComponentById(ResourceTable.Id_text_eveven);
        textEleven.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Text textLast = (Text) component.findComponentById(ResourceTable.Id_text_last);
        textLast.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        Checkbox checkbox = (Checkbox) component.findComponentById(ResourceTable.Id_check_box);
        checkbox.setFont(MainAbility.createFont(context, OSWALD_STENCBAB));
        TextField textField = (TextField) component.findComponentById(ResourceTable.Id_text_field);
        textField.setFont(MainAbility.createFont(context, ROBOTO_BOLD));
        Button button2 = (Button) component.findComponentById(ResourceTable.Id_button_bold);
        button2.setFont(MainAbility.createFont(context, ROBOTO_BOLD));
        Component customDialogButton = component.findComponentById(ResourceTable.Id_button_default);
        customDialogButton.setClickedListener(componentData -> showCustomDialog());
        Component customDialog = component.findComponentById(ResourceTable.Id_load_absolute);
        customDialog.setClickedListener(componentData -> showAbsoluteDialog());
        return component;
    }

    /**
     * To showCustomDialog.
     */
    private void showCustomDialog() {
        CustomDialog customDialog = new CustomDialog(getFractionAbility());
        customDialog.setTitle("Sample Dialog");
        customDialog.setToText("Custom Typeface Dialog");
        customDialog.setAutoClosable(true);
        customDialog.setOnConfirmListener(string -> {
            customDialog.destroy();
        });
        customDialog.show();
    }

    /**
     * To showAbsoluteDialog.
     */
    private void showAbsoluteDialog() {
        CustomDialog customDialog = new CustomDialog(getFractionAbility());
        customDialog.setAbsoluteText("Demo of loading a font from an absolute path.\\nLoaded from" + FIRST_FILE_PATH);
        customDialog.setAutoClosable(true);
        customDialog.setOnConfirmListener(string -> {
            customDialog.destroy();
        });
        customDialog.show();
    }
}