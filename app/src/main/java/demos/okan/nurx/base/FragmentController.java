package demos.okan.nurx.base;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Base Controller class to handle Adding, Replacing, Removing Fragments.
 *
 * @author osayilgan.
 */
public class FragmentController {

    private FragmentManager fragmentManager;

    /**
     * Constructor for Controller Class.
     *
     * NOTE : Tried SingleTon, but It loses it's state since the Fragment Manager is produced from different Activities,
     * but with SingleTon, it generates the Fragment Manager from first Activity attached.
     *
     * @param baseActivity      BaseActivity Instance to reach out Activity Context Based Resources.
     */
    protected FragmentController(BaseActivity baseActivity) {
        this.fragmentManager = baseActivity.getSupportFragmentManager();
    }

    /**
     * Replaces Fragment with the one inside the Fragment Container.
     * Animation : Fade-In-Out Animation with 500ms animation duration.
     *
     * @param fragment      Fragment to insert into given FragmentContainer.
     * @param containerId   ContainerId to insert Fragment.
     */
    protected void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack, boolean isAnimated) {

        /* Invalidation Errors Check */
        if (containerId == 0 || fragment == null || fragmentManager == null) return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (addToBackStack) transaction.addToBackStack(fragment.getClass().getSimpleName());

        if (isAnimated) transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                android.R.animator.fade_in, android.R.animator.fade_out);

        transaction.replace(containerId, fragment, fragment.getClass().getSimpleName());

        /* If we want to add Fragments to BackStack We cannot use synchronous processing with commitNow */
        if (addToBackStack) {
            transaction.commit();
        } else {
            transaction.commitNow();
        }
    }
    
    /**
     * Checks if the Given Fragment Container with given ID has any Fragments inside it.
     *
     * @param fragmentContainerId   ID of the Fragment Container.
     * @return                      True, if there is at least a fragment inside this Container.
     */
    protected boolean isFragmentContainerEmpty(int fragmentContainerId) {
        return (fragmentManager.findFragmentById(fragmentContainerId) == null);
    }

    /**
     * Pops All of the Fragments from the Fragment Manager's Back Stack.
     *
     * @param callback      Interface to send Feedback when the PopBackStack is executed.
     */
    protected void popFragmentStack(IBackStack callback) {

        if (callback != null) {
            fragmentManager.addOnBackStackChangedListener(() -> {
                if (fragmentManager.getBackStackEntryCount() == 0) callback.onPopBackStack();
            });
        }

        int backStackCount = fragmentManager.getBackStackEntryCount();

        for (int i = 0; i < backStackCount; i++) {
            if (fragmentManager.getBackStackEntryCount() > 0) fragmentManager.popBackStack();
        }
    }

    /**
     * Removes the last fragment from Fragment Manager's Stack.
     */
    protected void onBackPressed() {

        /* PopBackStack */
        fragmentManager.popBackStack();
    }

    /**
     * Checks if there is at least one Fragment in the Fragment's Back Stack.
     *
     * @return
     */
    protected boolean isFragmentStackEmpty() {
        return fragmentManager.getBackStackEntryCount() <= 0;
    }

    /**
     * Interface to notify when the The PopBackStack Operation is Finished.
     */
    public interface IBackStack {
        void onPopBackStack();
    }
}
