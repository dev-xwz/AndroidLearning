package com.xwz.androidlearning.injection.component;

import com.xwz.androidlearning.injection.module.ActivityModule;
import com.xwz.androidlearning.injection.module.FragmentModule;
import com.xwz.androidlearning.injection.scope.ConfigPersistent;

import dagger.Component;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example ViewModels).
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
    FragmentComponent fragmentComponent(ActivityModule activityModule, FragmentModule fragmentModule);
}
