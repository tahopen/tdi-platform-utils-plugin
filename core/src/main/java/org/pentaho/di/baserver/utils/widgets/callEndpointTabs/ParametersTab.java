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

package org.pentaho.di.baserver.utils.widgets.callEndpointTabs;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.di.baserver.utils.CallEndpointMeta;
import org.pentaho.di.baserver.utils.widgets.TableViewBuilder;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;

import java.util.ArrayList;
import java.util.List;

public class ParametersTab extends Tab {

  private final TableView queryParameters;

  public ParametersTab( CTabFolder tabFolder, PropsUI props, TransMeta transMeta, CallEndpointMeta metaInfo,
                        ModifyListener modifyListener, String stepName, LogChannel log ) {
    super( tabFolder, BaseMessages.getString( PKG, "CallEndpointDialog.TabItem.Parameters.Title" ), props );

    ColumnInfo cFieldName =
        new ColumnInfo( BaseMessages.getString( PKG, "CallEndpointDialog.TabItem.Parameters.Field" ),
            ColumnInfo.COLUMN_TYPE_CCOMBO, new String[] { "" }, false );

    ColumnInfo cParameter =
        new ColumnInfo( BaseMessages.getString( PKG, "CallEndpointDialog.TabItem.Parameters.Parameter" ),
            ColumnInfo.COLUMN_TYPE_TEXT, false );
    cParameter.setUsingVariables( true );

    ColumnInfo cDefaultValue =
        new ColumnInfo( BaseMessages.getString( PKG, "CallEndpointDialog.TabItem.Parameters.Default" ),
            ColumnInfo.COLUMN_TYPE_TEXT, false );
    cDefaultValue.setUsingVariables( true );
    cDefaultValue.setToolTip( BaseMessages.getString( PKG, "SetSessionVariableDialog.Column.DefaultValue.Tooltip" ) );

    queryParameters = new TableViewBuilder( props, this, transMeta )
        .addColumnInfo( cFieldName )
        .addColumnInfo( cParameter )
        .addColumnInfo( cDefaultValue )
        .setRowsCount( metaInfo.getFieldName().length )
        .setModifyListener( modifyListener )
        .setLeftPlacement( LEFT_PLACEMENT )
        .setRightPlacement( RIGHT_PLACEMENT )
        .setTopPlacement( 0 )
        .setBottomPlacement( 100 )
        .build();

    StepMeta stepMeta = transMeta.findStep( stepName );
    if ( stepMeta != null ) {
      try {
        // get field names from previous steps
        RowMetaInterface row = transMeta.getPrevStepFields( stepMeta );
        List<String> entries = new ArrayList<String>();
        for ( int i = 0; i < row.size(); i++ ) {
          entries.add( row.getValueMeta( i ).getName() );
        }
        String[] fieldNames = entries.toArray( new String[ entries.size() ] );

        // sort field names and add them to the combo box
        Const.sortStrings( fieldNames );
        cFieldName.setComboValues( fieldNames );
      } catch ( KettleException e ) {
        log.logError( BaseMessages.getString( PKG, "System.Dialog.GetFieldsFailed.Message" ) );
      }
    }
  }

  @Override public void loadData( CallEndpointMeta meta ) {
    for ( int i = 0; i < meta.getFieldName().length; i++ ) {
      TableItem item = queryParameters.table.getItem( i );
      int index = 0;
      item.setText( ++index, Const.NVL( meta.getFieldName()[ i ], "" ) );
      item.setText( ++index, Const.NVL( meta.getParameter()[ i ], "" ) );
      item.setText( ++index, Const.NVL( meta.getDefaultValue()[ i ], "" ) );
    }
    queryParameters.setRowNums();
    queryParameters.optWidth( true );
  }

  @Override public void saveData( CallEndpointMeta meta ) {
    int count = queryParameters.nrNonEmpty();
    meta.allocate( count );
    for ( int i = 0; i < count; i++ ) {
      TableItem item = queryParameters.getNonEmpty( i );
      int index = 0;
      meta.getFieldName()[ i ] = item.getText( ++index );
      meta.getParameter()[ i ] = item.getText( ++index );
      meta.getDefaultValue()[ i ] = item.getText( ++index );
    }
  }
}
