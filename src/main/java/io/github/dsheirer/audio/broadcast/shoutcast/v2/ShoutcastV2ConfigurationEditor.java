/*******************************************************************************
 * sdrtrunk
 * Copyright (C) 2014-2017 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 ******************************************************************************/
package io.github.dsheirer.audio.broadcast.shoutcast.v2;

import io.github.dsheirer.alias.AliasModel;
import io.github.dsheirer.audio.broadcast.BroadcastConfiguration;
import io.github.dsheirer.audio.broadcast.BroadcastConfigurationEditor;
import io.github.dsheirer.audio.broadcast.BroadcastEvent;
import io.github.dsheirer.audio.broadcast.BroadcastModel;
import io.github.dsheirer.audio.broadcast.BroadcastServerType;
import io.github.dsheirer.icon.IconManager;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoutcastV2ConfigurationEditor extends BroadcastConfigurationEditor
{
    private final static Logger mLog = LoggerFactory.getLogger(ShoutcastV2ConfigurationEditor.class);

    private static final int ONE_MINUTE_MS = 60000;

    private JTextField mName;
    private JTextField mServer;
    private JTextField mPort;
    private JTextField mStreamID;
    private JTextField mUserID;
    private JTextField mPassword;
    private JTextField mGenre;
    private JCheckBox mPublic;
    private JSlider mDelay;
    private JLabel mDelayValue;
    private JSlider mMaximumRecordingAge;
    private JLabel mMaximumRecordingAgeValue;
    private JCheckBox mEnabled;
    private JButton mSaveButton;
    private JButton mResetButton;

    public ShoutcastV2ConfigurationEditor(BroadcastModel broadcastModel, AliasModel aliasModel, IconManager iconManager)
    {
        super(broadcastModel, aliasModel, iconManager);
        init();
    }

    private void init()
    {
        setLayout(new MigLayout("fill,wrap 2", "[align right][grow,fill]",
            "[][][][][][][][][][][][][][][][grow,fill]"));
        setPreferredSize(new Dimension(150, 400));

        JLabel channelLabel = new JLabel("Shoutcast (v2) Stream");

        ImageIcon icon = mIconManager.getScaledIcon(BroadcastServerType.SHOUTCAST_V2.getIconPath(), 25);
        channelLabel.setIcon(icon);

        add(channelLabel, "span, align center");

        add(new JLabel("Name:"));
        mName = new JTextField();
        mName.getDocument().addDocumentListener(this);
        mName.setToolTipText("Unique name for this stream");
        add(mName);

        add(new JLabel("Server:"));
        mServer = new JTextField();
        mServer.getDocument().addDocumentListener(this);
        mServer.setToolTipText("Server address (e.g. audio1.shoutcast.com or localhost)");
        add(mServer);

        add(new JLabel("Port:"));
        mPort = new JTextField();
        mPort.getDocument().addDocumentListener(this);
        mPort.setToolTipText("Server port number (e.g. 80)");
        add(mPort);

        add(new JLabel("Stream ID:"));
        mStreamID = new JTextField();
        mStreamID.getDocument().addDocumentListener(this);
        mStreamID.setToolTipText("Stream Identifier)");
        add(mStreamID);

        add(new JLabel("User ID:"));
        mUserID = new JTextField();
        mUserID.getDocument().addDocumentListener(this);
        mUserID.setToolTipText("User ID/Name");
        add(mUserID);

        add(new JLabel("Password:"));
        mPassword = new JTextField();
        mPassword.getDocument().addDocumentListener(this);
        mPassword.setToolTipText("Password for the stream");
        add(mPassword);

        add(new JLabel("Genre:"));
        mGenre = new JTextField();
        mGenre.getDocument().addDocumentListener(this);
        mGenre.setToolTipText("Genre (e.g. public safety)");
        add(mGenre);

        add(new JLabel("Public:"));
        mPublic = new JCheckBox();
        mPublic.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setModified(true);
            }
        });
        mPublic.setToolTipText("Designate stream as public (checked) or private");
        add(mPublic);

        add(new JLabel());
        mDelayValue = new JLabel("0 Minutes");
        add(mDelayValue);

        add(new JLabel("Delay:"));
        mDelay = new JSlider(0, 60, 0);
        mDelay.setMajorTickSpacing(10);
        mDelay.setMinorTickSpacing(5);
        mDelay.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                mDelayValue.setText(mDelay.getValue() + " Minutes");
                setModified(true);
            }
        });
        mDelay.setToolTipText("Audio broadcast delay in minutes");
        add(mDelay);

        add(new JLabel());
        mMaximumRecordingAgeValue = new JLabel("1 Minute");
        add(mMaximumRecordingAgeValue);

        add(new JLabel("Age Limit:"));
        mMaximumRecordingAge = new JSlider(1,60,5);
        mMaximumRecordingAge.setMajorTickSpacing(10);
        mMaximumRecordingAge.setMinorTickSpacing(5);
        mMaximumRecordingAge.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int value = mMaximumRecordingAge.getValue();

                mMaximumRecordingAgeValue.setText(value + " Minute" + (value > 1 ? "s" : ""));
                setModified(true);
            }
        });
        mMaximumRecordingAge.setToolTipText("Maximum recording age (in addition to delay) to stream");
        add(mMaximumRecordingAge);

        add(new JLabel("Enabled:")); //Empty
        mEnabled = new JCheckBox();
        mEnabled.setToolTipText("Enable (checked) or disable this stream");
        mEnabled.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setModified(true);
            }
        });
        add(mEnabled);

        mSaveButton = new JButton("Save");
        mSaveButton.setEnabled(false);
        mSaveButton.setToolTipText("Click to save configuration information");
        mSaveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                save();
            }
        });
        add(mSaveButton);

        mResetButton = new JButton("Reset");
        mResetButton.setEnabled(false);
        mResetButton.setToolTipText("Click to reset any changes you've made since the last save");
        mResetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setModified(false);
                setItem(getItem());
            }
        });
        add(mResetButton);
    }

    @Override
    public void setModified(boolean modified)
    {
        super.setModified(modified);

        mSaveButton.setEnabled(modified);
        mResetButton.setEnabled(modified);
    }

    @Override
    public void setItem(BroadcastConfiguration item)
    {
        super.setItem(item);

        if(hasItem())
        {
            ShoutcastV2Configuration config = (ShoutcastV2Configuration) getItem();

            mName.setText(config.getName());
            mServer.setText(config.getHost());
            mPort.setText(config.getPort() != 0 ? String.valueOf(config.getPort()) : null);
            mStreamID.setText(String.valueOf(config.getStreamID()));
            mUserID.setText(config.getUserID());
            mPassword.setText(config.getPassword());
            mGenre.setText(config.getGenre());
            mPublic.setSelected(config.isPublic());
            mDelay.setValue((int) (config.getDelay() / ONE_MINUTE_MS));
            mMaximumRecordingAge.setValue((int)config.getMaximumRecordingAge() / ONE_MINUTE_MS);
            mEnabled.setSelected(config.isEnabled());
        }
        else
        {
            mName.setText(null);
            mServer.setText(null);
            mPort.setText(null);
            mStreamID.setText(null);
            mUserID.setText(null);
            mPassword.setText(null);
            mGenre.setText(null);
            mPublic.setSelected(true);
            mDelay.setValue(0);
            mMaximumRecordingAge.setValue(1);
            mEnabled.setSelected(false);
        }

        setModified(false);
    }

    @Override
    public void save()
    {
        ShoutcastV2Configuration config = (ShoutcastV2Configuration) getItem();

        if(validateConfiguration())
        {
            updateConfigurationName(config, mName.getText());
            config.setHost(mServer.getText());
            config.setPort(getPort());
            config.setUserID(mUserID.getText());
            config.setPassword(mPassword.getText());
            config.setStreamID(getStreamID());
            config.setGenre(mGenre.getText());
            config.setPublic(mPublic.isSelected());
            config.setDelay(mDelay.getValue() * ONE_MINUTE_MS);
            config.setMaximumRecordingAge(mMaximumRecordingAge.getValue() * ONE_MINUTE_MS);
            config.setEnabled(mEnabled.isSelected());

            setModified(false);

            mBroadcastModel.process(new BroadcastEvent(getItem(), BroadcastEvent.Event.CONFIGURATION_CHANGE));
        }
    }

    /**
     * Validates the user specified values in the controls prior to saving them to the configuration
     */
    private boolean validateConfiguration()
    {
        String name = mName.getText();

        //A unique stream name is required
        if(!mBroadcastModel.isUniqueName(name, getItem()))
        {
            String message = (name == null || name.isEmpty()) ?
                "Please specify a stream name." : "Stream name " + name +
                "is already in use.\nPlease choose another name.";

            JOptionPane.showMessageDialog(ShoutcastV2ConfigurationEditor.this,
                message, "Invalid Stream Name", JOptionPane.ERROR_MESSAGE);

            mName.requestFocus();

            return false;
        }

        //Server address is required
        if(!validateTextField(mServer, "Invalid Server Address", "Please specify a server address."))
        {
            return true;
        }

        //Port is required
        if(!validateIntegerTextField(mPort, "Invalid Port Number", "Please specify a port number (1 <> 65535)", 1, 65535))
        {
            return true;
        }

        //Stream ID is required
        if(!validateIntegerTextField(mStreamID, "Invalid Stream ID", "Please specify a stream ID of 1 or higher", 1, Integer.MAX_VALUE))
        {
            return true;
        }

        //User ID is required
        if(!validateTextField(mUserID, "Invalid User ID", "Please specify a user ID/name."))
        {
            return true;
        }

        //Password is required
        if(!validateTextField(mPassword, "Invalid Password", "Please specify a password."))
        {
            return true;
        }

        return true;
    }

    /**
     * Parses an Integer from the port text control or returns 0 if the control doesn't contain an integer value.
     */
    private int getPort()
    {
        String port = mPort.getText();

        if(port != null && !port.isEmpty())
        {
            try
            {
                return Integer.parseInt(port);
            }
            catch(Exception e)
            {
                //Do nothing, we couldn't parse the number value
            }
        }

        return 0;
    }

    /**
     * Parses an Integer from the port text control or returns 0 if the control doesn't contain an integer value.
     */
    private int getStreamID()
    {
        String streamID = mStreamID.getText();

        if(streamID != null && !streamID.isEmpty())
        {
            try
            {
                return Integer.parseInt(streamID);
            }
            catch(Exception e)
            {
                //Do nothing, we couldn't parse the number value
            }
        }

        return 0;
    }
}
