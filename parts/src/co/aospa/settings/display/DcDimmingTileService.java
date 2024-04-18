/*
 * Copyright (C) 2023 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.aospa.settings.display;

import static co.aospa.settings.display.DisplayConstants.*;

import android.content.Context;
import android.os.SystemProperties;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import co.aospa.settings.R;
import co.aospa.settings.utils.FileUtils;

public class DcDimmingTileService extends TileService {

    private Context context;
    private Tile tile;

    private int currentDcDimmingMode;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    private void updateCurrentDcDimmingMode() {
        currentDcDimmingMode = SystemProperties.getInt(DC_DIMMING_PROP, MODE_OFF);
    }

    private void updateDcDimmingTile() {
        String enabled = context.getResources().getString(R.string.oled_hbm_title_enabled);
        String disabled = context.getResources().getString(R.string.oled_hbm_title_disabled);

        tile.setState(currentDcDimmingMode != 0 ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setContentDescription(currentDcDimmingMode != 0 ? enabled : disabled);
        tile.setSubtitle(currentDcDimmingMode != 0 ?  enabled : disabled);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        tile = getQsTile();
        if (!FileUtils.fileExists(DC_DIMMING_NODE)) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.setSubtitle(getResources().getString(R.string.kernel_not_supported));
            tile.updateTile();
            return;
        }
        
        updateCurrentDcDimmingMode();
        updateDcDimmingTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        updateCurrentDcDimmingMode();
        currentDcDimmingMode = currentDcDimmingMode != MODE_ON ? MODE_ON : MODE_OFF;
        FileUtils.writeLine(DC_DIMMING_NODE, String.valueOf(currentDcDimmingMode == MODE_ON ? MODE_ON : MODE_OFF));
        SystemProperties.set(DC_DIMMING_PROP, Integer.toString(currentDcDimmingMode));
        updateDcDimmingTile();
    }
}
