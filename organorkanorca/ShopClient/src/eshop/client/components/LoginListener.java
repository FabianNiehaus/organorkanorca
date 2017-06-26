package eshop.client.components;

import eshop.common.data_objects.Person;

public interface LoginListener {

    public void loginCancelled();

    public void logout();

    public void userLoggedIn(Person user);
}
