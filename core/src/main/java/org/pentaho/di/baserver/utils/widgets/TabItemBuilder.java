/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License, version 2 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/gpl-2.0.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 *
 * Copyright 2006 - 2017 Hitachi Vantara.  All rights reserved.
 */

package org.pentaho.di.baserver.utils.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.di.ui.core.PropsUI;

public class TabItemBuilder extends WidgetBuilder<Composite> {
  private String text;

  public TabItemBuilder setText( String text ) {
    this.text = text;
    return this;
  }

  public TabItemBuilder( CTabFolder parent, PropsUI props ) {
    super( parent, props );
  }

  @Override
  protected Composite createWidget( Composite parent ) {
    setTopPlacement( 0 );
    setBottomPlacement( 100 );
    setLeftPlacement( 0 );
    setRightPlacement( 100 );
    Composite serverTabItemControl = createServerTabItemControl( parent, SWT.NONE );
    serverTabItemControl.setLayout( new FormLayout() );
    // create group
    CTabItem tabItem = createCTabItem( (CTabFolder) parent, SWT.NONE );
    tabItem.setText( this.text );
    tabItem.setControl( serverTabItemControl );

    return serverTabItemControl;
  }

  protected Composite createServerTabItemControl( Composite parent, int i ) {
    return new Composite( parent, i );
  }

  protected CTabItem createCTabItem( CTabFolder parent, int i ) {
    return new CTabItem( parent, i );
  }
}
