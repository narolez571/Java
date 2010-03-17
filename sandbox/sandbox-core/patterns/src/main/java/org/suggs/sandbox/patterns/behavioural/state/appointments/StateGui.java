/*
 * StateGui.java created on 3 Aug 2009 06:59:31 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.behavioural.state.appointments;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Main GUI for showing the state changes
 * 
 * @author suggitpe
 * @version 1.0 3 Aug 2009
 */
public class StateGui implements ActionListener
{

    private JFrame mMainFrame_ = new JFrame( "State Pattern Example" );
    private JPanel mEditPanel_ = new JPanel();
    private JPanel mControlPanel_ = new JPanel();
    private CalendarEditor mCalendar_ = null;
    private JButton mSaveButton_ = new JButton( "Save" );
    private JButton mExitButton_ = new JButton( "Exit" );

    /**
     * Constructs a new instance.
     * 
     * @param aCalendar
     *            a calendar editor to delegate save and edit actions
     *            to
     */
    public StateGui( CalendarEditor aCalendar )
    {
        mCalendar_ = aCalendar;
    }

    /**
     * Creates the actual GUI
     */
    public void createGui()
    {
        Container content = mMainFrame_.getContentPane();
        content.setLayout( new BoxLayout( content, BoxLayout.Y_AXIS ) );

        // build and add the edit panel
        mEditPanel_.setLayout( new BorderLayout() );
        List<Appointment> apps = mCalendar_.getAppointments();
        JTable table = new JTable( new StateTableModel( apps.toArray( new Appointment[apps.size()] ) ) );
        mEditPanel_.add( new JScrollPane( table ) );
        content.add( mEditPanel_ );

        // build and add the control panel
        mControlPanel_.add( mSaveButton_ );
        mControlPanel_.add( mExitButton_ );
        content.add( mControlPanel_ );

        mSaveButton_.addActionListener( this );
        mExitButton_.addActionListener( this );

        mMainFrame_.addWindowListener( new WindowCloseManager() );
        mMainFrame_.pack();
        mMainFrame_.setVisible( true );
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed( ActionEvent event )
    {
        Object originator = event.getSource();
        if ( originator == mSaveButton_ )
        {
            saveAppointments();
        }
        else if ( originator == mExitButton_ )
        {
            exitApplication();
        }
    }

    /**
     * Call save on the calendar editor
     */
    private void saveAppointments()
    {
        mCalendar_.save();
    }

    /**
     * Process to close out the application GUI.
     */
    private void exitApplication()
    {
        System.exit( 0 );
    }

    /**
     * Class to allow us to provide a different implementation for
     * closing down
     * 
     * @author suggitpe
     * @version 1.0 3 Aug 2009
     */
    private class WindowCloseManager extends WindowAdapter
    {

        /**
         * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
         */
        @Override
        public void windowClosing( WindowEvent event )
        {
            exitApplication();
        }
    }

    /**
     * Private class to manage the data for the state table
     * 
     * @author suggitpe
     * @version 1.0 3 Aug 2009
     */
    private class StateTableModel extends AbstractTableModel
    {

        private final String[] mColumnNames = { "Appointment", "Contacts", "Location",
                                               "Start Date", "End Date" };
        private Appointment[] mData;

        /**
         * Constructs a new instance.
         * 
         * @param aData
         *            appointment data array
         */
        public StateTableModel( Appointment[] aData )
        {
            mData = aData;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        @Override
        public int getColumnCount()
        {
            return mColumnNames.length;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        @Override
        public int getRowCount()
        {
            return mData.length;
        }

        /**
         * @see javax.swing.table.AbstractTableModel#getColumnName(int)
         */
        @Override
        public String getColumnName( int column )
        {
            return mColumnNames[column];
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        @Override
        public Object getValueAt( int row, int col )
        {
            Object ret = null;
            switch ( col )
            {
                case 0:
                    ret = mData[row].getReason();
                    break;
                case 1:
                    ret = mData[row].getContacts();
                    break;
                case 2:
                    ret = mData[row].getLocation();
                    break;
                case 3:
                    ret = mData[row].getStartDate();
                    break;
                case 4:
                    ret = mData[row].getEndDate();
                    break;
            }
            return ret;
        }

        /**
         * @see javax.swing.table.AbstractTableModel#isCellEditable(int,
         *      int)
         */
        @Override
        public boolean isCellEditable( int row, int col )
        {
            return ( ( col == 0 || col == 2 ) ) ? true : false;
        }

        /**
         * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
         *      int, int)
         */
        @Override
        public void setValueAt( Object value, int row, int col )
        {
            switch ( col )
            {
                case 0:
                    mData[row].setReason( (String) value );
                    mCalendar_.edit();
                    break;
                case 1:
                    break;
                case 2:
                    mData[row].setLocation( (String) value );
                    mCalendar_.edit();
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }
}
