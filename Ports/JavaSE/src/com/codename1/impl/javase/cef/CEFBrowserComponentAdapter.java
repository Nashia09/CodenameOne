/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.impl.javase.cef;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.events.ActionEvent;
import java.lang.ref.WeakReference;

/**
 *
 * @author shannah
 */
public class CEFBrowserComponentAdapter implements CEFBrowserComponentListener {
    private WeakReference<BrowserComponent> bcRef;
    
    public CEFBrowserComponentAdapter(BrowserComponent bc) {
        this.bcRef = new WeakReference<BrowserComponent>(bc);
    }
    
    
    @Override
    public void onError(ActionEvent e) {
        BrowserComponent bc = bcRef.get();
        if (bc != null) {
            bc.fireWebEvent(BrowserComponent.onError, e);
        }
    }

    @Override
    public void onStart(ActionEvent e) {
        BrowserComponent bc = bcRef.get();
        if (bc != null) {
            bc.fireWebEvent(BrowserComponent.onStart, e);
        }
    }

    @Override
    public void onLoad(ActionEvent e) {
        BrowserComponent bc = bcRef.get();
        if (bc != null) {
            bc.fireWebEvent(BrowserComponent.onLoad, e);
        }
    }

    @Override
    public boolean shouldNavigate(String url) {
        BrowserComponent bc = bcRef.get();
        if (bc != null) {
            return bc.fireBrowserNavigationCallbacks(url);
        }
        return true;
    }
    
}
