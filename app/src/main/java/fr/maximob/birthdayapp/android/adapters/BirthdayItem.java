package fr.maximob.birthdayapp.android.adapters;

import fr.maximob.birthdayapp.android.models.Birthday;

public class BirthdayItem extends ListItem {

    public Birthday birthday;

    public BirthdayItem(Birthday birthday) {
        this.birthday = birthday;
    }

    @Override
    public int getType() {
        return TYPE_BIRTHDAY;
    }
}
