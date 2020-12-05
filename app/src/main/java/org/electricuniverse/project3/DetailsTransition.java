package org.electricuniverse.project3;

import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)

public class DetailsTransition extends TransitionSet
{
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DetailsTransition()
    {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).addTransition(new ChangeImageTransform());
    }
}
