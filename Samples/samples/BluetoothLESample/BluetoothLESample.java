//require CN1Bluetooth
//require CN1JSON
package com.codename1.samples;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.bluetoothle.Bluetooth;
import com.codename1.components.MultiButton;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class BluetoothLESample {

    private Form current;
    private Resources theme;

    private Bluetooth bt;

    private Form main;
    private Container devicesCnt;
    private Map devices = new HashMap();

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
        bt = new Bluetooth();
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        main = new Form("Bluetooth Demo");
        main.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        main.add(new Button(new Command("enable bluetooth") {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    if (!bt.isEnabled()) {
                        bt.enable();
                    }
                    if (!bt.hasPermission()) {
                        bt.requestPermission();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
        main.add(new Button(new Command("initialize") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    bt.initialize(true, false, "bluetoothleplugin");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
        main.add(new Button(new Command("start scan") {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    bt.startScan(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            try {
                                JSONObject res = (JSONObject) evt.getSource();
                                System.out.println("response " + res);

                                if (res.getString("status").equals("scanResult")) {
                                    //if this is a new device add it
                                    if (!devices.containsKey(res.getString("address"))) {
                                        devices.put(res.getString("address"), res);
                                        updateUI();
                                    }
                                }
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }, null, true, Bluetooth.SCAN_MODE_LOW_POWER, Bluetooth.MATCH_MODE_STICKY,
                            Bluetooth.MATCH_NUM_MAX_ADVERTISEMENT, Bluetooth.CALLBACK_TYPE_ALL_MATCHES);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));

        main.add(new Button(new Command("stop scan") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    bt.stopScan();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));

        devicesCnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        main.add(devicesCnt);
        main.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

    private void updateUI() throws JSONException {
        devicesCnt.removeAll();
        Set keys = devices.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            String address = (String) iterator.next();
            JSONObject obj = (JSONObject) devices.get(address);
            MultiButton mb = new MultiButton(obj.getString("name"));
            mb.setTextLine2(address);
            devicesCnt.add(mb);
        }
        devicesCnt.revalidate();
    }

}