package parkme.projectm.hr.parkme.Fragments;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;

/**
 * Created by Mihael on 8.4.2015..
 */
public interface ChooseCarFragmentCallback {

    public void displayAddCarDialog();

    public void refreshActivity();

    public void updateOrRemoveFavoriteCar(FavouriteCar favoriteCar);

}
