package demos.okan.nurx.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import demos.okan.nurx.R;

/**
 * @author Okan SAYILGAN
 */
public abstract class BaseActivity extends AppCompatActivity {

    /** Instance of Fragment Controller Class */
    private FragmentController fragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Override Activity's Enter and Exit Animations as Fade in and out */
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        fragmentController = new FragmentController(this);
    }

    @Override
    public void finish() {
        super.finish();

        /* Override Activity's Enter and Exit Animations as Fade in and out */
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {

        if ( ! fragmentController.isFragmentStackEmpty()) {
            fragmentController.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    /********************************** Fragment Controller Methods ***********************************/

    /**
     * Inserts given Fragment in to Given Fragment Container.
     *
     * @param fragment              Fragment to insert into given Container.
     * @param fragmentContainerId   Container ID assigned for Fragment Container which will host to the given Fragment.
     * @param addToBackStack        If True, Fragment will be added to the Fragment Manager's back stack with Fragment's Name.
     * @param isAnimated            If True, Fragment will be animated with Fade-In-Out Animation.
     */
    public void attachFragment(Fragment fragment, int fragmentContainerId, boolean addToBackStack, boolean isAnimated) {
        fragmentController.replaceFragment(fragment, fragmentContainerId, addToBackStack, isAnimated);
    }

    /**
     * Inserts given Fragment in to Given Fragment Container if the Container is Empty (Has no Fragment Inside).
     * && this Fragment is not going to be added to Back Stack of the Fragment Manager.
     *
     * @param fragment              Fragment to insert into given Container.
     * @param fragmentContainerId   Container ID assigned for Fragment Container which will host to the given Fragment.
     * @param isAnimated            If True, Fragment will be animated with Fade-In-Out Animation.
     */
    public void attachRootFragment(Fragment fragment, int fragmentContainerId, boolean isAnimated) {
        if (fragmentController.isFragmentContainerEmpty(fragmentContainerId)) {
            fragmentController.replaceFragment(fragment, fragmentContainerId, false, isAnimated);
        }
    }

    /**
     * Cleans up (Pops up all of the Fragments) the Fragment Manager's Stack.
     */
    public void popFragmentStack() {
        fragmentController.popFragmentStack(null);
    }
}
