package com.forsenboyz.rise42.server.objects.actions;

import com.forsenboyz.rise42.server.objects.managers.ProjectileManager;

public interface Castable {

    void onCast(ProjectileManager projectileManager);
}
