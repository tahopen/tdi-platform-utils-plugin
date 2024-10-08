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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.di.ui.core.PropsUI;

public class ImageBuilder extends WidgetBuilder<Label> {

  private Image image;

  public ImageBuilder setImage( final Image image ) {
    this.image = image;
    return this;
  }

  public ImageBuilder( Composite parent, PropsUI props ) {
    super( parent, props );
  }

  @Override
  protected Label createWidget( Composite parent ) {
    Label label = createLabel( parent, SWT.RIGHT );
    label.setImage( image );
    return label;
  }

  protected Label createLabel( Composite parent, int i ) {
    return new Label( parent, i );
  }
}
