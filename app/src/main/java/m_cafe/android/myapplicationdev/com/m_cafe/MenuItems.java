package m_cafe.android.myapplicationdev.com.m_cafe;

import java.io.Serializable;

/**
 * Created by 15004557 on 18/7/2017.
 */

public class MenuItems implements Serializable {
    private String menu_item_desc;
    private String menu_item_id;

    public MenuItems(String menu_item_desc, String menu_item_id) {
        this.menu_item_desc = menu_item_desc;
        this.menu_item_id = menu_item_id;
    }

    public String getMenu_item_desc() {
        return menu_item_desc;
    }

    public void setMenu_item_desc(String menu_item_desc) {
        this.menu_item_desc = menu_item_desc;
    }

    public String getMenu_item_id() {
        return menu_item_id;
    }

    public void setMenu_item_id(String menu_item_id) {
        this.menu_item_id = menu_item_id;
    }

    @Override
    public String toString() {
        return menu_item_desc;
    }
}
