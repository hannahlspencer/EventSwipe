package eventswipe;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * The application's main frame.
 */
public class EventSwipeView extends FrameView {

    public EventSwipeView(SingleFrameApplication app) {
        super(app);
        ResourceMap resourceMap = Application.getInstance(eventswipe.EventSwipeApp.class)
                                  .getContext().getResourceMap(EventSwipeView.class);
        titleInputDefault = resourceMap.getString("eventTitleInputDefault");
        fileInputDefault = resourceMap.getString("fileInputDefault");
        idInputDefault = resourceMap.getString("idInputDefault");
        recordingAllText = resourceMap.getString("checkingModeToggle1.text");
        checkingListsText = resourceMap.getString("checkingListsText");
        onlineModeTooltipText = resourceMap.getString("onlineModeToggler.toolTipText");
        offlineModeTooltipText = resourceMap.getString("offlineModeTooltipText");
        initComponents();
        updateBookingPanel(false);
        try {
            Image i = ImageIO.read(getClass().getResource("/eventswipe/resources/yourLogoLarge.jpeg"));
            this.getFrame().setIconImage(i);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        this.getFrame().setPreferredSize(new Dimension(750, 500));
        this.getFrame().setResizable(false);
        usernameInput.requestFocusInWindow();
    }

    javax.swing.Action save = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            app.saveAttendeesToFile();
        }
    };
    javax.swing.Action toggleBooking = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            checkingModeToggle1.doClick();
        }
    };
    javax.swing.Action checkBooking = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            searchButton.doClick();
        }
    };
    javax.swing.Action toggleConnection = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            onlineModeToggle.doClick();
        }
    };
    javax.swing.Action refreshAttendees = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            refreshAttendeesButton.doClick();
        }
    };

    @Action
    public void initialFinishAction() {
        if (app.isOnlineMode()) {
            this.switchToPanel(finishPanel);
        }
        else {
            app.saveAndFinish();
        }
    }

    @Action
    public void finishAction() {
        try {
            app.finish(markAbsentOption.isSelected(), notifyAbsentOption.isSelected());
        } catch (Exception ex) {
            Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Error marking absentees. You'll have to do this manually.",
                                          "Marking absentee error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        try {
            app.finish(false, false);
        } catch (Exception ex) {} //app can't throw excpetion with app.finish(false)
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = app.getMainFrame();
            aboutBox = new EventSwipeAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        app.show(aboutBox);
    }

    @Action
    public void logIn() {
        String username = usernameInput.getText();
        char[] password = passwordInput.getPassword();
        passwordInput.setText("");
        if (username.isEmpty() || username.equals(usernameInputDefault)) {
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Please enter your CareerHub username.",
                                          "No username",
                                          JOptionPane.ERROR_MESSAGE);
        }
        else if (password.length == 0) {
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Please enter your CareerHub password.",
                                          "No password",
                                          JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                if(app.logIn(username, password)) {
                    app.setOnlineModeFlag(true);
                    switchToPanel(onlineConfigPanel);
                }
                else {
                    JOptionPane.showMessageDialog(app.getMainFrame(),
                                                  "Your username or password were incorrect. " +
                                                  "Please try again",
                                                  "Login error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(app.getMainFrame(),
                                              "There was a problem logging in. " +
                                              "Please try again",
                                              "Login error",
                                              JOptionPane.ERROR_MESSAGE);
            } finally {
                Arrays.fill(password,'0');
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        toggleMenuItem = new javax.swing.JMenuItem();
        connectionMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        configPanel = new javax.swing.JPanel();
        okConfigButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        bookingDetailsPanel = new javax.swing.JPanel();
        entrySlot1Panel = new javax.swing.JPanel();
        bookingListBrowseButton1 = new javax.swing.JButton();
        entrySlotBookingListFilePathInput1 = new javax.swing.JFormattedTextField();
        entrySlotBookingListLabel1 = new javax.swing.JLabel();
        entrySlot2Panel = new javax.swing.JPanel();
        entrySlotBookingListFilePathInput2 = new javax.swing.JFormattedTextField();
        entrySlotBookingListLabel2 = new javax.swing.JLabel();
        bookingListBrowseButton2 = new javax.swing.JButton();
        entrySlot4Panel = new javax.swing.JPanel();
        entrySlotBookingListLabel4 = new javax.swing.JLabel();
        entrySlotBookingListFilePathInput4 = new javax.swing.JFormattedTextField();
        bookingListBrowseButton4 = new javax.swing.JButton();
        entrySlot5Panel = new javax.swing.JPanel();
        entrySlotBookingListFilePathInput5 = new javax.swing.JFormattedTextField();
        bookingListBrowseButton5 = new javax.swing.JButton();
        entrySlotBookingListLabel5 = new javax.swing.JLabel();
        waitingListFilePanel = new javax.swing.JPanel();
        waitingListFileLabel = new javax.swing.JLabel();
        waitingListBrowseButton = new javax.swing.JButton();
        waitingListFilePathInput = new javax.swing.JFormattedTextField();
        entrySlot3Panel = new javax.swing.JPanel();
        bookingListBrowseButton3 = new javax.swing.JButton();
        entrySlotBookingListLabel3 = new javax.swing.JLabel();
        entrySlotBookingListFilePathInput3 = new javax.swing.JFormattedTextField();
        slotsPanel = new javax.swing.JPanel();
        entrySlotsSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, EventSwipeData.MAX_ENTRY_SLOTS, 1));
        entrySlotsLabel = new javax.swing.JLabel();
        waitingListRadioPanel = new javax.swing.JPanel();
        noWaitingListRadioButton = new javax.swing.JRadioButton();
        yesWaitingListRadioButton = new javax.swing.JRadioButton();
        waitingListLabel = new javax.swing.JLabel();
        configBackButton = new javax.swing.JButton();
        requireBookingPanel = new javax.swing.JPanel();
        yesBookingRadioButton = new javax.swing.JRadioButton();
        noBookingRadioButton = new javax.swing.JRadioButton();
        requireBookingLabel = new javax.swing.JLabel();
        eventTitlePanel = new javax.swing.JPanel();
        eventTitleInputLabel = new javax.swing.JLabel();
        eventTitleInput = new javax.swing.JFormattedTextField();
        requireBookingButtonGroup = new javax.swing.ButtonGroup();
        waitingListButtonGroup = new javax.swing.ButtonGroup();
        preConfigPanel = new javax.swing.JPanel();
        titleLoginPanel = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        logInLabel = new javax.swing.JLabel();
        useOfflineLabel = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        startOfflineButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        useOfflineDescription = new javax.swing.JTextArea();
        passwordLabel = new javax.swing.JLabel();
        logInButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        usernameInput = new javax.swing.JTextField();
        loginOfflineSeparator = new javax.swing.JSeparator();
        titlePanel = new javax.swing.JPanel();
        smallLogoLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        mainTitle = new javax.swing.JLabel();
        onlineConfigPanel = new javax.swing.JPanel();
        bookingDetailsPanel1 = new javax.swing.JPanel();
        entrySlotsLabel1 = new javax.swing.JLabel();
        entrySlotsSpinner1 = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, EventSwipeData.MAX_ENTRY_SLOTS, 1));
        slot1DetailsPanel = new javax.swing.JPanel();
        generatedTitleLabel1 = new javax.swing.JLabel();
        searchEventsButton1 = new javax.swing.JButton();
        entrySlotIdLabel1 = new javax.swing.JLabel();
        generatedTitle1 = new javax.swing.JTextField();
        loadEventButton1 = new javax.swing.JButton();
        entrySlotIdInput1 = new javax.swing.JFormattedTextField();
        slot1Label = new javax.swing.JLabel();
        slot2DetailsPanel = new javax.swing.JPanel();
        generatedTitle2 = new javax.swing.JTextField();
        entrySlotIdLabel2 = new javax.swing.JLabel();
        slot2Label = new javax.swing.JLabel();
        entrySlotIdInput2 = new javax.swing.JFormattedTextField();
        loadEventButton2 = new javax.swing.JButton();
        searchEventsButton2 = new javax.swing.JButton();
        generatedTitleLabel2 = new javax.swing.JLabel();
        slot3DtailsPanel = new javax.swing.JPanel();
        generatedTitleLabel3 = new javax.swing.JLabel();
        searchEventsButton3 = new javax.swing.JButton();
        generatedTitle3 = new javax.swing.JTextField();
        slot3Label = new javax.swing.JLabel();
        entrySlotIdLabel3 = new javax.swing.JLabel();
        loadEventButton3 = new javax.swing.JButton();
        entrySlotIdInput3 = new javax.swing.JFormattedTextField();
        slot4DetailsPanel = new javax.swing.JPanel();
        searchEventsButton4 = new javax.swing.JButton();
        slot4Label = new javax.swing.JLabel();
        loadEventButton4 = new javax.swing.JButton();
        generatedTitleLabel4 = new javax.swing.JLabel();
        entrySlotIdInput4 = new javax.swing.JFormattedTextField();
        generatedTitle4 = new javax.swing.JTextField();
        entrySlotIdLabel4 = new javax.swing.JLabel();
        slot5DetailsPanel = new javax.swing.JPanel();
        entrySlotIdLabel5 = new javax.swing.JLabel();
        entrySlotIdInput5 = new javax.swing.JFormattedTextField();
        loadEventButton5 = new javax.swing.JButton();
        generatedTitle5 = new javax.swing.JTextField();
        generatedTitleLabel5 = new javax.swing.JLabel();
        searchEventsButton5 = new javax.swing.JButton();
        slot5Label = new javax.swing.JLabel();
        waitingListOptionsPanel = new javax.swing.JPanel();
        loadWaitingListLabel = new javax.swing.JLabel();
        noLoadWaitingListRadioButton = new javax.swing.JRadioButton();
        yesLoadWaitingListRadioButton = new javax.swing.JRadioButton();
        onlineConfigBackButton = new javax.swing.JButton();
        aboutEventLabel = new javax.swing.JLabel();
        okConfigButton1 = new javax.swing.JButton();
        mainOnlinePanel = new javax.swing.JPanel();
        searchInput = new javax.swing.JFormattedTextField();
        searchButton = new javax.swing.JButton();
        bookingStatusScrollPane1 = new javax.swing.JScrollPane();
        bookingStatusTextArea1 = new javax.swing.JTextArea();
        backButton1 = new javax.swing.JButton();
        finishButton = new javax.swing.JButton();
        searchInputLabel = new javax.swing.JLabel();
        checkingModeToggle1 = new javax.swing.JToggleButton();
        onlineModeToggle = new javax.swing.JToggleButton();
        bookingStatusPanel = new javax.swing.JPanel();
        totalLabel = new javax.swing.JLabel();
        entrySlotDisplayTextField1 = new javax.swing.JFormattedTextField();
        attendeeCountLabel1 = new javax.swing.JLabel();
        statusDisplayTextField1 = new javax.swing.JFormattedTextField();
        attendeeCountLabel2 = new javax.swing.JLabel();
        localAttendeeCountTextField = new javax.swing.JFormattedTextField();
        statusLabel1 = new javax.swing.JLabel();
        entrySlotLabel1 = new javax.swing.JLabel();
        thisMachineLabel = new javax.swing.JLabel();
        totalAttendeeCountDisplay = new javax.swing.JFormattedTextField();
        refreshAttendeesButton = new javax.swing.JButton();
        finishPanel = new javax.swing.JPanel();
        finishCloseButton = new javax.swing.JButton();
        finishBackButton = new javax.swing.JButton();
        finishPanelTitle = new javax.swing.JLabel();
        markAbsentOption = new javax.swing.JCheckBox();
        notifyAbsentOption = new javax.swing.JCheckBox();

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(eventswipe.EventSwipeApp.class).getContext().getResourceMap(EventSwipeView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        toggleMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        toggleMenuItem.setText(resourceMap.getString("toggleMenuItem.text")); // NOI18N
        toggleMenuItem.setEnabled(false);
        toggleMenuItem.setName("toggleMenuItem"); // NOI18N
        toggleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(toggleMenuItem);

        connectionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        connectionMenuItem.setText(resourceMap.getString("connectionMenuItem.text")); // NOI18N
        connectionMenuItem.setEnabled(false);
        connectionMenuItem.setName("connectionMenuItem"); // NOI18N
        connectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(connectionMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(eventswipe.EventSwipeApp.class).getContext().getActionMap(EventSwipeView.class, this);
        saveMenuItem.setAction(actionMap.get("saveAttendeesToFile")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        configPanel.setMinimumSize(new java.awt.Dimension(720, 350));
        configPanel.setName("configPanel"); // NOI18N
        configPanel.setPreferredSize(new java.awt.Dimension(720, 540));

        okConfigButton.setText(resourceMap.getString("okConfigButton.text")); // NOI18N
        okConfigButton.setName("okConfigButton"); // NOI18N
        okConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okConfigButtonActionPerformed(evt);
            }
        });

        titleLabel.setFont(resourceMap.getFont("titleLabel.font")); // NOI18N
        titleLabel.setText(resourceMap.getString("titleLabel.text")); // NOI18N
        titleLabel.setName("titleLabel"); // NOI18N

        bookingDetailsPanel.setEnabled(false);
        bookingDetailsPanel.setMaximumSize(new java.awt.Dimension(808, 472));
        bookingDetailsPanel.setName("bookingDetailsPanel"); // NOI18N
        bookingDetailsPanel.setRequestFocusEnabled(false);

        entrySlot1Panel.setName("entrySlot1Panel"); // NOI18N
        entrySlot1Panel.setPreferredSize(new java.awt.Dimension(475, 40));

        bookingListBrowseButton1.setText(resourceMap.getString("bookingListBrowseButton1.text")); // NOI18N
        bookingListBrowseButton1.setName("bookingListBrowseButton1"); // NOI18N
        bookingListBrowseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        entrySlotBookingListFilePathInput1.setText(fileInputDefault);
        entrySlotBookingListFilePathInput1.setName("entrySlotBookingListFilePathInput1"); // NOI18N
        entrySlotBookingListFilePathInput1.setPreferredSize(new java.awt.Dimension(200, 20));
        entrySlotBookingListFilePathInput1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        entrySlotBookingListLabel1.setText(resourceMap.getString("entrySlotBookingListLabel1.text")); // NOI18N
        entrySlotBookingListLabel1.setName("entrySlotBookingListLabel1"); // NOI18N

        javax.swing.GroupLayout entrySlot1PanelLayout = new javax.swing.GroupLayout(entrySlot1Panel);
        entrySlot1Panel.setLayout(entrySlot1PanelLayout);
        entrySlot1PanelLayout.setHorizontalGroup(
            entrySlot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotBookingListLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotBookingListFilePathInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingListBrowseButton1)
                .addContainerGap())
        );
        entrySlot1PanelLayout.setVerticalGroup(
            entrySlot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(entrySlot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotBookingListLabel1)
                    .addComponent(entrySlotBookingListFilePathInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingListBrowseButton1))
                .addContainerGap())
        );

        entrySlot2Panel.setName("entrySlot2Panel"); // NOI18N
        entrySlot2Panel.setPreferredSize(new java.awt.Dimension(475, 40));

        entrySlotBookingListFilePathInput2.setText(fileInputDefault);
        entrySlotBookingListFilePathInput2.setName("entrySlotBookingListFilePathInput2"); // NOI18N
        entrySlotBookingListFilePathInput2.setPreferredSize(new java.awt.Dimension(200, 20));
        entrySlotBookingListFilePathInput2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        entrySlotBookingListLabel2.setText(resourceMap.getString("entrySlotBookingListLabel2.text")); // NOI18N
        entrySlotBookingListLabel2.setName("entrySlotBookingListLabel2"); // NOI18N

        bookingListBrowseButton2.setText(resourceMap.getString("bookingListBrowseButton2.text")); // NOI18N
        bookingListBrowseButton2.setName("bookingListBrowseButton2"); // NOI18N
        bookingListBrowseButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        javax.swing.GroupLayout entrySlot2PanelLayout = new javax.swing.GroupLayout(entrySlot2Panel);
        entrySlot2Panel.setLayout(entrySlot2PanelLayout);
        entrySlot2PanelLayout.setHorizontalGroup(
            entrySlot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotBookingListLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotBookingListFilePathInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingListBrowseButton2)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        entrySlot2PanelLayout.setVerticalGroup(
            entrySlot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(entrySlot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotBookingListLabel2)
                    .addComponent(bookingListBrowseButton2)
                    .addComponent(entrySlotBookingListFilePathInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        entrySlot4Panel.setName("entrySlot4Panel"); // NOI18N
        entrySlot4Panel.setPreferredSize(new java.awt.Dimension(475, 40));
        entrySlot4Panel.setRequestFocusEnabled(false);

        entrySlotBookingListLabel4.setText(resourceMap.getString("entrySlotBookingListLabel4.text")); // NOI18N
        entrySlotBookingListLabel4.setName("entrySlotBookingListLabel4"); // NOI18N

        entrySlotBookingListFilePathInput4.setText(fileInputDefault);
        entrySlotBookingListFilePathInput4.setName("entrySlotBookingListFilePathInput4"); // NOI18N
        entrySlotBookingListFilePathInput4.setPreferredSize(new java.awt.Dimension(200, 20));
        entrySlotBookingListFilePathInput4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        bookingListBrowseButton4.setText(resourceMap.getString("bookingListBrowseButton4.text")); // NOI18N
        bookingListBrowseButton4.setName("bookingListBrowseButton4"); // NOI18N
        bookingListBrowseButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        javax.swing.GroupLayout entrySlot4PanelLayout = new javax.swing.GroupLayout(entrySlot4Panel);
        entrySlot4Panel.setLayout(entrySlot4PanelLayout);
        entrySlot4PanelLayout.setHorizontalGroup(
            entrySlot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot4PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotBookingListLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotBookingListFilePathInput4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingListBrowseButton4)
                .addGap(3504, 3504, 3504))
        );
        entrySlot4PanelLayout.setVerticalGroup(
            entrySlot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot4PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(entrySlot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(entrySlotBookingListLabel4)
                    .addComponent(entrySlotBookingListFilePathInput4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingListBrowseButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        entrySlot5Panel.setName("entrySlot5Panel"); // NOI18N
        entrySlot5Panel.setPreferredSize(new java.awt.Dimension(475, 40));

        entrySlotBookingListFilePathInput5.setText(fileInputDefault);
        entrySlotBookingListFilePathInput5.setName("entrySlotBookingListFilePathInput5"); // NOI18N
        entrySlotBookingListFilePathInput5.setPreferredSize(new java.awt.Dimension(200, 20));
        entrySlotBookingListFilePathInput5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        bookingListBrowseButton5.setText(resourceMap.getString("bookingListBrowseButton5.text")); // NOI18N
        bookingListBrowseButton5.setName("bookingListBrowseButton5"); // NOI18N
        bookingListBrowseButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        entrySlotBookingListLabel5.setText(resourceMap.getString("entrySlotBookingListLabel5.text")); // NOI18N
        entrySlotBookingListLabel5.setName("entrySlotBookingListLabel5"); // NOI18N

        javax.swing.GroupLayout entrySlot5PanelLayout = new javax.swing.GroupLayout(entrySlot5Panel);
        entrySlot5Panel.setLayout(entrySlot5PanelLayout);
        entrySlot5PanelLayout.setHorizontalGroup(
            entrySlot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot5PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotBookingListLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotBookingListFilePathInput5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingListBrowseButton5)
                .addContainerGap())
        );
        entrySlot5PanelLayout.setVerticalGroup(
            entrySlot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot5PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(entrySlot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotBookingListLabel5)
                    .addComponent(entrySlotBookingListFilePathInput5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingListBrowseButton5))
                .addContainerGap())
        );

        waitingListFilePanel.setName("waitingListFilePanel"); // NOI18N
        waitingListFilePanel.setPreferredSize(new java.awt.Dimension(500, 40));

        waitingListFileLabel.setText(resourceMap.getString("waitingListFileLabel.text")); // NOI18N
        waitingListFileLabel.setName("waitingListFileLabel"); // NOI18N

        waitingListBrowseButton.setText(resourceMap.getString("waitingListBrowseButton.text")); // NOI18N
        waitingListBrowseButton.setName("waitingListBrowseButton"); // NOI18N
        waitingListBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        waitingListFilePathInput.setText(fileInputDefault);
        waitingListFilePathInput.setName("waitingListFilePathInput"); // NOI18N
        waitingListFilePathInput.setPreferredSize(new java.awt.Dimension(200, 20));
        waitingListFilePathInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        javax.swing.GroupLayout waitingListFilePanelLayout = new javax.swing.GroupLayout(waitingListFilePanel);
        waitingListFilePanel.setLayout(waitingListFilePanelLayout);
        waitingListFilePanelLayout.setHorizontalGroup(
            waitingListFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListFilePanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(waitingListFileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waitingListFilePathInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waitingListBrowseButton)
                .addGap(87, 87, 87))
        );
        waitingListFilePanelLayout.setVerticalGroup(
            waitingListFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(waitingListFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(waitingListBrowseButton)
                    .addComponent(waitingListFilePathInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(waitingListFileLabel))
                .addContainerGap())
        );

        entrySlot3Panel.setName("entrySlot3Panel"); // NOI18N
        entrySlot3Panel.setPreferredSize(new java.awt.Dimension(475, 40));

        bookingListBrowseButton3.setText(resourceMap.getString("bookingListBrowseButton3.text")); // NOI18N
        bookingListBrowseButton3.setName("bookingListBrowseButton3"); // NOI18N
        bookingListBrowseButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        entrySlotBookingListLabel3.setText(resourceMap.getString("entrySlotBookingListLabel3.text")); // NOI18N
        entrySlotBookingListLabel3.setName("entrySlotBookingListLabel3"); // NOI18N

        entrySlotBookingListFilePathInput3.setText(fileInputDefault);
        entrySlotBookingListFilePathInput3.setName("entrySlotBookingListFilePathInput3"); // NOI18N
        entrySlotBookingListFilePathInput3.setPreferredSize(new java.awt.Dimension(200, 20));
        entrySlotBookingListFilePathInput3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        javax.swing.GroupLayout entrySlot3PanelLayout = new javax.swing.GroupLayout(entrySlot3Panel);
        entrySlot3Panel.setLayout(entrySlot3PanelLayout);
        entrySlot3PanelLayout.setHorizontalGroup(
            entrySlot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotBookingListLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotBookingListFilePathInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingListBrowseButton3)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        entrySlot3PanelLayout.setVerticalGroup(
            entrySlot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entrySlot3PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(entrySlot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotBookingListLabel3)
                    .addComponent(entrySlotBookingListFilePathInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingListBrowseButton3))
                .addContainerGap())
        );

        slotsPanel.setName("slotsPanel"); // NOI18N

        entrySlotsSpinner.setName("entrySlotsSpinner"); // NOI18N
        entrySlotsSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                entrySlotsSpinnerStateChanged(evt);
            }
        });

        entrySlotsLabel.setText(resourceMap.getString("entrySlotsLabel.text")); // NOI18N
        entrySlotsLabel.setName("entrySlotsLabel"); // NOI18N

        javax.swing.GroupLayout slotsPanelLayout = new javax.swing.GroupLayout(slotsPanel);
        slotsPanel.setLayout(slotsPanelLayout);
        slotsPanelLayout.setHorizontalGroup(
            slotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slotsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrySlotsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        slotsPanelLayout.setVerticalGroup(
            slotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slotsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotsLabel)
                    .addComponent(entrySlotsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        waitingListRadioPanel.setName("waitingListRadioPanel"); // NOI18N

        waitingListButtonGroup.add(noWaitingListRadioButton);
        noWaitingListRadioButton.setSelected(true);
        noWaitingListRadioButton.setText(resourceMap.getString("noWaitingListRadioButton.text")); // NOI18N
        noWaitingListRadioButton.setName("noWaitingListRadioButton"); // NOI18N
        noWaitingListRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noWaitingListRadioButtonActionPerformed(evt);
            }
        });

        waitingListButtonGroup.add(yesWaitingListRadioButton);
        yesWaitingListRadioButton.setText(resourceMap.getString("yesWaitingListRadioButton.text")); // NOI18N
        yesWaitingListRadioButton.setName("yesWaitingListRadioButton"); // NOI18N
        yesWaitingListRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesWaitingListRadioButtonActionPerformed(evt);
            }
        });

        waitingListLabel.setText(resourceMap.getString("waitingListLabel.text")); // NOI18N
        waitingListLabel.setName("waitingListLabel"); // NOI18N

        javax.swing.GroupLayout waitingListRadioPanelLayout = new javax.swing.GroupLayout(waitingListRadioPanel);
        waitingListRadioPanel.setLayout(waitingListRadioPanelLayout);
        waitingListRadioPanelLayout.setHorizontalGroup(
            waitingListRadioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListRadioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(waitingListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(yesWaitingListRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noWaitingListRadioButton)
                .addContainerGap())
        );
        waitingListRadioPanelLayout.setVerticalGroup(
            waitingListRadioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListRadioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(waitingListRadioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(waitingListLabel)
                    .addComponent(yesWaitingListRadioButton)
                    .addComponent(noWaitingListRadioButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout bookingDetailsPanelLayout = new javax.swing.GroupLayout(bookingDetailsPanel);
        bookingDetailsPanel.setLayout(bookingDetailsPanelLayout);
        bookingDetailsPanelLayout.setHorizontalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slotsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(waitingListRadioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(waitingListFilePanel, 0, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlot5Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlot4Panel, 0, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlot3Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlot2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlot1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))))
        );
        bookingDetailsPanelLayout.setVerticalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entrySlot1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slotsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(entrySlot2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlot3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlot4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlot5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(waitingListRadioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(waitingListFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        configBackButton.setText(resourceMap.getString("configBackButton.text")); // NOI18N
        configBackButton.setName("configBackButton"); // NOI18N
        configBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configBackButtonActionPerformed(evt);
            }
        });

        requireBookingPanel.setName("requireBookingPanel"); // NOI18N

        requireBookingButtonGroup.add(yesBookingRadioButton);
        yesBookingRadioButton.setText(resourceMap.getString("yesBookingRadioButton.text")); // NOI18N
        yesBookingRadioButton.setName("yesBookingRadioButton"); // NOI18N
        yesBookingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesBookingRadioButtonActionPerformed(evt);
            }
        });

        requireBookingButtonGroup.add(noBookingRadioButton);
        noBookingRadioButton.setSelected(true);
        noBookingRadioButton.setText(resourceMap.getString("noBookingRadioButton.text")); // NOI18N
        noBookingRadioButton.setName("noBookingRadioButton"); // NOI18N
        noBookingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noBookingRadioButtonActionPerformed(evt);
            }
        });

        requireBookingLabel.setText(resourceMap.getString("requireBookingLabel.text")); // NOI18N
        requireBookingLabel.setName("requireBookingLabel"); // NOI18N

        javax.swing.GroupLayout requireBookingPanelLayout = new javax.swing.GroupLayout(requireBookingPanel);
        requireBookingPanel.setLayout(requireBookingPanelLayout);
        requireBookingPanelLayout.setHorizontalGroup(
            requireBookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requireBookingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(requireBookingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(yesBookingRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noBookingRadioButton)
                .addContainerGap())
        );
        requireBookingPanelLayout.setVerticalGroup(
            requireBookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requireBookingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(requireBookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requireBookingLabel)
                    .addComponent(yesBookingRadioButton)
                    .addComponent(noBookingRadioButton))
                .addContainerGap())
        );

        eventTitlePanel.setName("eventTitlePanel"); // NOI18N
        eventTitlePanel.setPreferredSize(new java.awt.Dimension(750, 42));

        eventTitleInputLabel.setText(resourceMap.getString("eventTitleInputLabel.text")); // NOI18N
        eventTitleInputLabel.setName("eventTitleInputLabel"); // NOI18N

        eventTitleInput.setText(titleInputDefault);
        eventTitleInput.setName("eventTitleInput"); // NOI18N
        eventTitleInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        javax.swing.Action checkConfiguration = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                checkConfiguration();
            }
        };
        eventTitleInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            "checkConfiguration");
        eventTitleInput.getActionMap().put("checkConfiguration", checkConfiguration);

        javax.swing.GroupLayout eventTitlePanelLayout = new javax.swing.GroupLayout(eventTitlePanel);
        eventTitlePanel.setLayout(eventTitlePanelLayout);
        eventTitlePanelLayout.setHorizontalGroup(
            eventTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eventTitleInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eventTitleInput, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );
        eventTitlePanelLayout.setVerticalGroup(
            eventTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(eventTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventTitleInputLabel)
                    .addComponent(eventTitleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel)
                            .addComponent(requireBookingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eventTitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE))
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addComponent(configBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 519, Short.MAX_VALUE)
                        .addComponent(okConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(1, 1, 1)
                .addComponent(eventTitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requireBookingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(configBackButton)
                    .addComponent(okConfigButton))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        preConfigPanel.setFocusable(false);
        preConfigPanel.setMinimumSize(new java.awt.Dimension(720, 350));
        preConfigPanel.setName("preConfigPanel"); // NOI18N

        titleLoginPanel.setFocusable(false);
        titleLoginPanel.setName("titleLoginPanel"); // NOI18N

        loginPanel.setFocusable(false);
        loginPanel.setName("loginPanel"); // NOI18N

        logInLabel.setFont(resourceMap.getFont("logInLabel.font")); // NOI18N
        logInLabel.setText(resourceMap.getString("logInLabel.text")); // NOI18N
        logInLabel.setFocusable(false);
        logInLabel.setName("logInLabel"); // NOI18N

        useOfflineLabel.setFont(resourceMap.getFont("useOfflineLabel.font")); // NOI18N
        useOfflineLabel.setText(resourceMap.getString("useOfflineLabel.text")); // NOI18N
        useOfflineLabel.setFocusable(false);
        useOfflineLabel.setName("useOfflineLabel"); // NOI18N

        passwordInput.setText(resourceMap.getString("passwordInput.text")); // NOI18N
        passwordInput.setName("passwordInput"); // NOI18N
        passwordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginInputKeyPressed(evt);
            }
        });

        startOfflineButton.setText(resourceMap.getString("startOfflineButton.text")); // NOI18N
        startOfflineButton.setName("startOfflineButton"); // NOI18N
        startOfflineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startOfflineButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        useOfflineDescription.setBackground(resourceMap.getColor("useOfflineDescription.background")); // NOI18N
        useOfflineDescription.setColumns(20);
        useOfflineDescription.setEditable(false);
        useOfflineDescription.setFont(resourceMap.getFont("useOfflineDescription.font")); // NOI18N
        useOfflineDescription.setLineWrap(true);
        useOfflineDescription.setRows(5);
        useOfflineDescription.setText(resourceMap.getString("useOfflineDescription.text")); // NOI18N
        useOfflineDescription.setWrapStyleWord(true);
        useOfflineDescription.setAutoscrolls(false);
        useOfflineDescription.setBorder(null);
        useOfflineDescription.setFocusable(false);
        useOfflineDescription.setName("useOfflineDescription"); // NOI18N
        jScrollPane1.setViewportView(useOfflineDescription);

        passwordLabel.setText(resourceMap.getString("passwordLabel.text")); // NOI18N
        passwordLabel.setFocusable(false);
        passwordLabel.setName("passwordLabel"); // NOI18N

        logInButton.setAction(actionMap.get("logIn")); // NOI18N
        logInButton.setText(resourceMap.getString("logInButton.text")); // NOI18N
        logInButton.setName("logInButton"); // NOI18N

        usernameLabel.setText(resourceMap.getString("usernameLabel.text")); // NOI18N
        usernameLabel.setFocusable(false);
        usernameLabel.setName("usernameLabel"); // NOI18N

        usernameInput.setText(resourceMap.getString("usernameInput.text")); // NOI18N
        usernameInput.setName("usernameInput"); // NOI18N
        usernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginInputKeyPressed(evt);
            }
        });

        loginOfflineSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);
        loginOfflineSeparator.setName("loginOfflineSeparator"); // NOI18N

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logInLabel)
                    .addComponent(usernameLabel)
                    .addComponent(passwordLabel)
                    .addComponent(usernameInput)
                    .addComponent(passwordInput, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addComponent(logInButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(loginOfflineSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addComponent(useOfflineLabel)
                    .addComponent(startOfflineButton, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginOfflineSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(logInLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(usernameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(useOfflineLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logInButton)
                            .addComponent(startOfflineButton))))
                .addContainerGap())
        );

        titlePanel.setFocusable(false);
        titlePanel.setName("titlePanel"); // NOI18N

        smallLogoLabel.setIcon(resourceMap.getIcon("smallLogoLabel.icon")); // NOI18N
        smallLogoLabel.setText(resourceMap.getString("smallLogoLabel.text")); // NOI18N
        smallLogoLabel.setFocusable(false);
        smallLogoLabel.setName("smallLogoLabel"); // NOI18N

        versionLabel.setFont(resourceMap.getFont("versionLabel.font")); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("eventswipe/resources/EventSwipeApp"); // NOI18N
        versionLabel.setText(bundle.getString("Application.version")); // NOI18N
        versionLabel.setFocusable(false);
        versionLabel.setName("versionLabel"); // NOI18N

        mainTitle.setFont(resourceMap.getFont("mainTitle.font")); // NOI18N
        mainTitle.setText(resourceMap.getString("mainTitle.text")); // NOI18N
        mainTitle.setFocusable(false);
        mainTitle.setName("mainTitle"); // NOI18N

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(smallLogoLabel)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(versionLabel)
                        .addContainerGap())))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(mainTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionLabel))
                    .addComponent(smallLogoLabel))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout titleLoginPanelLayout = new javax.swing.GroupLayout(titleLoginPanel);
        titleLoginPanel.setLayout(titleLoginPanelLayout);
        titleLoginPanelLayout.setHorizontalGroup(
            titleLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titleLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        titleLoginPanelLayout.setVerticalGroup(
            titleLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout preConfigPanelLayout = new javax.swing.GroupLayout(preConfigPanel);
        preConfigPanel.setLayout(preConfigPanelLayout);
        preConfigPanelLayout.setHorizontalGroup(
            preConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, preConfigPanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(titleLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        preConfigPanelLayout.setVerticalGroup(
            preConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preConfigPanelLayout.createSequentialGroup()
                .addComponent(titleLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        onlineConfigPanel.setMinimumSize(new java.awt.Dimension(720, 400));
        onlineConfigPanel.setName("onlineConfigPanel"); // NOI18N
        onlineConfigPanel.setPreferredSize(new java.awt.Dimension(723, 400));

        bookingDetailsPanel1.setEnabled(false);
        bookingDetailsPanel1.setName("bookingDetailsPanel1"); // NOI18N

        entrySlotsLabel1.setText(resourceMap.getString("entrySlotsLabel1.text")); // NOI18N
        entrySlotsLabel1.setName("entrySlotsLabel1"); // NOI18N

        entrySlotsSpinner1.setName("entrySlotsSpinner1"); // NOI18N
        entrySlotsSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                entrySlotsSpinner1StateChanged(evt);
            }
        });

        slot1DetailsPanel.setName("slot1DetailsPanel"); // NOI18N

        generatedTitleLabel1.setText(resourceMap.getString("generatedTitleLabel1.text")); // NOI18N
        generatedTitleLabel1.setName("generatedTitleLabel1"); // NOI18N

        searchEventsButton1.setText(resourceMap.getString("searchEventsButton1.text")); // NOI18N
        searchEventsButton1.setName("searchEventsButton1"); // NOI18N
        searchEventsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        entrySlotIdLabel1.setText(resourceMap.getString("entrySlotIdLabel1.text")); // NOI18N
        entrySlotIdLabel1.setName("entrySlotIdLabel1"); // NOI18N

        generatedTitle1.setText(resourceMap.getString("generatedTitle1.text")); // NOI18N
        generatedTitle1.setEnabled(false);
        generatedTitle1.setName("generatedTitle1"); // NOI18N

        loadEventButton1.setText(resourceMap.getString("loadEventButton1.text")); // NOI18N
        loadEventButton1.setName("loadEventButton1"); // NOI18N
        loadEventButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        entrySlotIdInput1.setText(idInputDefault);
        entrySlotIdInput1.setName("entrySlotIdInput1"); // NOI18N
        entrySlotIdInput1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        entrySlotIdInput1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idInputKeyPressed(evt);
            }
        });

        slot1Label.setFont(resourceMap.getFont("slot1Label.font")); // NOI18N
        slot1Label.setText(resourceMap.getString("slot1Label.text")); // NOI18N
        slot1Label.setName("slot1Label"); // NOI18N

        javax.swing.GroupLayout slot1DetailsPanelLayout = new javax.swing.GroupLayout(slot1DetailsPanel);
        slot1DetailsPanel.setLayout(slot1DetailsPanelLayout);
        slot1DetailsPanelLayout.setHorizontalGroup(
            slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot1DetailsPanelLayout.createSequentialGroup()
                .addGroup(slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot1DetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedTitleLabel1)
                            .addComponent(entrySlotIdLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(slot1DetailsPanelLayout.createSequentialGroup()
                                .addComponent(entrySlotIdInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(generatedTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(slot1Label))
                .addContainerGap())
        );
        slot1DetailsPanelLayout.setVerticalGroup(
            slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot1DetailsPanelLayout.createSequentialGroup()
                .addComponent(slot1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchEventsButton1)
                    .addComponent(entrySlotIdLabel1)
                    .addComponent(loadEventButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot1DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel1))
                .addContainerGap())
        );

        slot2DetailsPanel.setName("slot2DetailsPanel"); // NOI18N

        generatedTitle2.setText(resourceMap.getString("generatedTitle2.text")); // NOI18N
        generatedTitle2.setEnabled(false);
        generatedTitle2.setName("generatedTitle2"); // NOI18N

        entrySlotIdLabel2.setText(resourceMap.getString("entrySlotIdLabel2.text")); // NOI18N
        entrySlotIdLabel2.setName("entrySlotIdLabel2"); // NOI18N

        slot2Label.setFont(resourceMap.getFont("slot2Label.font")); // NOI18N
        slot2Label.setText(resourceMap.getString("slot2Label.text")); // NOI18N
        slot2Label.setEnabled(false);
        slot2Label.setFocusable(false);
        slot2Label.setName("slot2Label"); // NOI18N

        entrySlotIdInput2.setText(idInputDefault);
        entrySlotIdInput2.setEnabled(false);
        entrySlotIdInput2.setName("entrySlotIdInput2"); // NOI18N
        entrySlotIdInput2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        entrySlotIdInput2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idInputKeyPressed(evt);
            }
        });

        loadEventButton2.setText(resourceMap.getString("loadEventButton2.text")); // NOI18N
        loadEventButton2.setEnabled(false);
        loadEventButton2.setName("loadEventButton2"); // NOI18N
        loadEventButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        searchEventsButton2.setText(resourceMap.getString("searchEventsButton2.text")); // NOI18N
        searchEventsButton2.setEnabled(false);
        searchEventsButton2.setName("searchEventsButton2"); // NOI18N
        searchEventsButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        generatedTitleLabel2.setText(resourceMap.getString("generatedTitleLabel2.text")); // NOI18N
        generatedTitleLabel2.setName("generatedTitleLabel2"); // NOI18N

        javax.swing.GroupLayout slot2DetailsPanelLayout = new javax.swing.GroupLayout(slot2DetailsPanel);
        slot2DetailsPanel.setLayout(slot2DetailsPanelLayout);
        slot2DetailsPanelLayout.setHorizontalGroup(
            slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot2DetailsPanelLayout.createSequentialGroup()
                .addGroup(slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot2DetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedTitleLabel2)
                            .addComponent(entrySlotIdLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(slot2DetailsPanelLayout.createSequentialGroup()
                                .addComponent(entrySlotIdInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(generatedTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(slot2Label))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        slot2DetailsPanelLayout.setVerticalGroup(
            slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot2DetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadEventButton2)
                    .addComponent(searchEventsButton2)
                    .addComponent(entrySlotIdLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot2DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        slot3DtailsPanel.setName("slot3DtailsPanel"); // NOI18N

        generatedTitleLabel3.setText(resourceMap.getString("generatedTitleLabel3.text")); // NOI18N
        generatedTitleLabel3.setName("generatedTitleLabel3"); // NOI18N

        searchEventsButton3.setText(resourceMap.getString("searchEventsButton3.text")); // NOI18N
        searchEventsButton3.setEnabled(false);
        searchEventsButton3.setName("searchEventsButton3"); // NOI18N
        searchEventsButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        generatedTitle3.setText(resourceMap.getString("generatedTitle3.text")); // NOI18N
        generatedTitle3.setEnabled(false);
        generatedTitle3.setName("generatedTitle3"); // NOI18N

        slot3Label.setFont(resourceMap.getFont("slot3Label.font")); // NOI18N
        slot3Label.setText(resourceMap.getString("slot3Label.text")); // NOI18N
        slot3Label.setEnabled(false);
        slot3Label.setFocusable(false);
        slot3Label.setName("slot3Label"); // NOI18N

        entrySlotIdLabel3.setText(resourceMap.getString("entrySlotIdLabel3.text")); // NOI18N
        entrySlotIdLabel3.setName("entrySlotIdLabel3"); // NOI18N

        loadEventButton3.setText(resourceMap.getString("loadEventButton3.text")); // NOI18N
        loadEventButton3.setEnabled(false);
        loadEventButton3.setName("loadEventButton3"); // NOI18N
        loadEventButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        entrySlotIdInput3.setText(idInputDefault);
        entrySlotIdInput3.setEnabled(false);
        entrySlotIdInput3.setName("entrySlotIdInput3"); // NOI18N
        entrySlotIdInput3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        entrySlotIdInput3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idInputKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout slot3DtailsPanelLayout = new javax.swing.GroupLayout(slot3DtailsPanel);
        slot3DtailsPanel.setLayout(slot3DtailsPanelLayout);
        slot3DtailsPanelLayout.setHorizontalGroup(
            slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot3DtailsPanelLayout.createSequentialGroup()
                .addGroup(slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot3DtailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(entrySlotIdLabel3)
                            .addComponent(generatedTitleLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(slot3DtailsPanelLayout.createSequentialGroup()
                                .addComponent(entrySlotIdInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(generatedTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)))
                    .addComponent(slot3Label))
                .addContainerGap())
        );
        slot3DtailsPanelLayout.setVerticalGroup(
            slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot3DtailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot3Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entrySlotIdLabel3)
                    .addComponent(searchEventsButton3)
                    .addComponent(loadEventButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot3DtailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel3))
                .addContainerGap())
        );

        slot4DetailsPanel.setName("slot4DetailsPanel"); // NOI18N

        searchEventsButton4.setText(resourceMap.getString("searchEventsButton4.text")); // NOI18N
        searchEventsButton4.setEnabled(false);
        searchEventsButton4.setName("searchEventsButton4"); // NOI18N
        searchEventsButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        slot4Label.setFont(resourceMap.getFont("slot4Label.font")); // NOI18N
        slot4Label.setText(resourceMap.getString("slot4Label.text")); // NOI18N
        slot4Label.setEnabled(false);
        slot4Label.setFocusable(false);
        slot4Label.setName("slot4Label"); // NOI18N

        loadEventButton4.setText(resourceMap.getString("loadEventButton4.text")); // NOI18N
        loadEventButton4.setEnabled(false);
        loadEventButton4.setName("loadEventButton4"); // NOI18N
        loadEventButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        generatedTitleLabel4.setText(resourceMap.getString("generatedTitleLabel4.text")); // NOI18N
        generatedTitleLabel4.setName("generatedTitleLabel4"); // NOI18N

        entrySlotIdInput4.setText(idInputDefault);
        entrySlotIdInput4.setEnabled(false);
        entrySlotIdInput4.setName("entrySlotIdInput4"); // NOI18N
        entrySlotIdInput4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        entrySlotIdInput4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idInputKeyPressed(evt);
            }
        });

        generatedTitle4.setEnabled(false);
        generatedTitle4.setName("generatedTitle4"); // NOI18N

        entrySlotIdLabel4.setText(resourceMap.getString("entrySlotIdLabel4.text")); // NOI18N
        entrySlotIdLabel4.setName("entrySlotIdLabel4"); // NOI18N

        javax.swing.GroupLayout slot4DetailsPanelLayout = new javax.swing.GroupLayout(slot4DetailsPanel);
        slot4DetailsPanel.setLayout(slot4DetailsPanelLayout);
        slot4DetailsPanelLayout.setHorizontalGroup(
            slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot4DetailsPanelLayout.createSequentialGroup()
                .addGroup(slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot4DetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedTitleLabel4)
                            .addComponent(entrySlotIdLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(slot4DetailsPanelLayout.createSequentialGroup()
                                .addComponent(entrySlotIdInput4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(generatedTitle4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(slot4Label))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        slot4DetailsPanelLayout.setVerticalGroup(
            slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot4DetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot4Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadEventButton4)
                    .addComponent(searchEventsButton4)
                    .addComponent(entrySlotIdLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot4DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel4))
                .addContainerGap())
        );

        slot5DetailsPanel.setName("slot5DetailsPanel"); // NOI18N

        entrySlotIdLabel5.setText(resourceMap.getString("entrySlotIdLabel5.text")); // NOI18N
        entrySlotIdLabel5.setName("entrySlotIdLabel5"); // NOI18N

        entrySlotIdInput5.setText(idInputDefault);
        entrySlotIdInput5.setEnabled(false);
        entrySlotIdInput5.setName("entrySlotIdInput5"); // NOI18N
        entrySlotIdInput5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });
        entrySlotIdInput5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idInputKeyPressed(evt);
            }
        });

        loadEventButton5.setText(resourceMap.getString("loadEventButton5.text")); // NOI18N
        loadEventButton5.setEnabled(false);
        loadEventButton5.setName("loadEventButton5"); // NOI18N
        loadEventButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        generatedTitle5.setEnabled(false);
        generatedTitle5.setName("generatedTitle5"); // NOI18N

        generatedTitleLabel5.setText(resourceMap.getString("generatedTitleLabel5.text")); // NOI18N
        generatedTitleLabel5.setName("generatedTitleLabel5"); // NOI18N

        searchEventsButton5.setText(resourceMap.getString("searchEventsButton5.text")); // NOI18N
        searchEventsButton5.setEnabled(false);
        searchEventsButton5.setName("searchEventsButton5"); // NOI18N
        searchEventsButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        slot5Label.setFont(resourceMap.getFont("slot5Label.font")); // NOI18N
        slot5Label.setText(resourceMap.getString("slot5Label.text")); // NOI18N
        slot5Label.setEnabled(false);
        slot5Label.setFocusable(false);
        slot5Label.setName("slot5Label"); // NOI18N

        javax.swing.GroupLayout slot5DetailsPanelLayout = new javax.swing.GroupLayout(slot5DetailsPanel);
        slot5DetailsPanel.setLayout(slot5DetailsPanelLayout);
        slot5DetailsPanelLayout.setHorizontalGroup(
            slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot5DetailsPanelLayout.createSequentialGroup()
                .addGroup(slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot5DetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(entrySlotIdLabel5)
                            .addComponent(generatedTitleLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(slot5DetailsPanelLayout.createSequentialGroup()
                                .addComponent(entrySlotIdInput5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton5))
                            .addComponent(generatedTitle5, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                    .addComponent(slot5Label))
                .addContainerGap())
        );
        slot5DetailsPanelLayout.setVerticalGroup(
            slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot5DetailsPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(slot5Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entrySlotIdLabel5)
                    .addComponent(searchEventsButton5)
                    .addComponent(loadEventButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot5DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel5))
                .addContainerGap())
        );

        waitingListOptionsPanel.setName("waitingListOptionsPanel"); // NOI18N

        loadWaitingListLabel.setText(resourceMap.getString("loadWaitingListLabel.text")); // NOI18N
        loadWaitingListLabel.setName("loadWaitingListLabel"); // NOI18N

        waitingListButtonGroup.add(noLoadWaitingListRadioButton);
        noLoadWaitingListRadioButton.setText(resourceMap.getString("noLoadWaitingListRadioButton.text")); // NOI18N
        noLoadWaitingListRadioButton.setName("noLoadWaitingListRadioButton"); // NOI18N

        waitingListButtonGroup.add(yesLoadWaitingListRadioButton);
        yesLoadWaitingListRadioButton.setText(resourceMap.getString("yesLoadWaitingListRadioButton.text")); // NOI18N
        yesLoadWaitingListRadioButton.setName("yesLoadWaitingListRadioButton"); // NOI18N

        javax.swing.GroupLayout waitingListOptionsPanelLayout = new javax.swing.GroupLayout(waitingListOptionsPanel);
        waitingListOptionsPanel.setLayout(waitingListOptionsPanelLayout);
        waitingListOptionsPanelLayout.setHorizontalGroup(
            waitingListOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loadWaitingListLabel)
                .addGap(4, 4, 4)
                .addComponent(yesLoadWaitingListRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noLoadWaitingListRadioButton)
                .addContainerGap())
        );
        waitingListOptionsPanelLayout.setVerticalGroup(
            waitingListOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingListOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(waitingListOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadWaitingListLabel)
                    .addComponent(yesLoadWaitingListRadioButton)
                    .addComponent(noLoadWaitingListRadioButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout bookingDetailsPanel1Layout = new javax.swing.GroupLayout(bookingDetailsPanel1);
        bookingDetailsPanel1.setLayout(bookingDetailsPanel1Layout);
        bookingDetailsPanel1Layout.setHorizontalGroup(
            bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(slot2DetailsPanel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(entrySlotsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entrySlotsSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(slot4DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(slot1DetailsPanel, 0, 328, Short.MAX_VALUE)
                    .addComponent(slot5DetailsPanel, 0, 328, Short.MAX_VALUE)
                    .addComponent(slot3DtailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                .addGap(7, 7, 7))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanel1Layout.createSequentialGroup()
                .addGap(505, 505, 505)
                .addComponent(waitingListOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );
        bookingDetailsPanel1Layout.setVerticalGroup(
            bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(entrySlotsSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(entrySlotsLabel1))
                    .addComponent(slot1DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot3DtailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot2DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot4DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slot5DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(waitingListOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        onlineConfigBackButton.setText(resourceMap.getString("onlineConfigBackButton.text")); // NOI18N
        onlineConfigBackButton.setName("onlineConfigBackButton"); // NOI18N
        onlineConfigBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineConfigBackButtonActionPerformed(evt);
            }
        });

        aboutEventLabel.setFont(resourceMap.getFont("aboutEventLabel.font")); // NOI18N
        aboutEventLabel.setText(resourceMap.getString("aboutEventLabel.text")); // NOI18N
        aboutEventLabel.setName("aboutEventLabel"); // NOI18N

        okConfigButton1.setText(resourceMap.getString("okConfigButton1.text")); // NOI18N
        okConfigButton1.setName("okConfigButton1"); // NOI18N
        okConfigButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okConfigButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout onlineConfigPanelLayout = new javax.swing.GroupLayout(onlineConfigPanel);
        onlineConfigPanel.setLayout(onlineConfigPanelLayout);
        onlineConfigPanelLayout.setHorizontalGroup(
            onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, onlineConfigPanelLayout.createSequentialGroup()
                        .addComponent(onlineConfigBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 510, Short.MAX_VALUE)
                        .addComponent(okConfigButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                        .addComponent(aboutEventLabel)
                        .addContainerGap(624, Short.MAX_VALUE))
                    .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                        .addComponent(bookingDetailsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))))
        );
        onlineConfigPanelLayout.setVerticalGroup(
            onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, onlineConfigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aboutEventLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingDetailsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(okConfigButton1)
                    .addComponent(onlineConfigBackButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainOnlinePanel.setMinimumSize(new java.awt.Dimension(720, 350));
        mainOnlinePanel.setName("mainOnlinePanel"); // NOI18N

        searchInput.setName("searchInput"); // NOI18N

        searchButton.setText(resourceMap.getString("searchButton.text")); // NOI18N
        searchButton.setFocusable(false);
        searchButton.setName("searchButton"); // NOI18N
        searchButton.setRequestFocusEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        bookingStatusScrollPane1.setBackground(null);
        bookingStatusScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bookingStatusScrollPane1.setFocusable(false);
        bookingStatusScrollPane1.setName("bookingStatusScrollPane1"); // NOI18N

        bookingStatusTextArea1.setBackground(null);
        bookingStatusTextArea1.setColumns(20);
        bookingStatusTextArea1.setEditable(false);
        bookingStatusTextArea1.setRows(5);
        bookingStatusTextArea1.setFocusable(false);
        bookingStatusTextArea1.setName("bookingStatusTextArea1"); // NOI18N
        bookingStatusTextArea1.setRequestFocusEnabled(false);
        bookingStatusScrollPane1.setViewportView(bookingStatusTextArea1);

        backButton1.setText(resourceMap.getString("backButton1.text")); // NOI18N
        backButton1.setFocusable(false);
        backButton1.setName("backButton1"); // NOI18N
        backButton1.setRequestFocusEnabled(false);
        backButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButton1ActionPerformed(evt);
            }
        });

        finishButton.setAction(actionMap.get("initialFinishAction")); // NOI18N
        finishButton.setText(resourceMap.getString("finishButton.text")); // NOI18N
        finishButton.setAutoscrolls(true);
        finishButton.setFocusable(false);
        finishButton.setName("finishButton"); // NOI18N
        finishButton.setRequestFocusEnabled(false);

        searchInputLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        searchInputLabel.setLabelFor(searchInput);
        searchInputLabel.setText(resourceMap.getString("searchInputLabel.text")); // NOI18N
        searchInputLabel.setFocusable(false);
        searchInputLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchInputLabel.setName("searchInputLabel"); // NOI18N

        checkingModeToggle1.setText(resourceMap.getString("checkingModeToggle1.text")); // NOI18N
        checkingModeToggle1.setToolTipText(resourceMap.getString("checkingModeToggle1.toolTipText")); // NOI18N
        checkingModeToggle1.setFocusPainted(false);
        checkingModeToggle1.setFocusable(false);
        checkingModeToggle1.setName("checkingModeToggle1"); // NOI18N
        checkingModeToggle1.setRequestFocusEnabled(false);
        checkingModeToggle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkingModeToggle1ActionPerformed(evt);
            }
        });

        onlineModeToggle.setIcon(resourceMap.getIcon("onlineModeToggler.icon")); // NOI18N
        onlineModeToggle.setSelected(true);
        onlineModeToggle.setText(resourceMap.getString("onlineModeToggler.text")); // NOI18N
        onlineModeToggle.setToolTipText(resourceMap.getString("onlineModeToggler.toolTipText")); // NOI18N
        onlineModeToggle.setFocusable(false);
        onlineModeToggle.setName("onlineModeToggler"); // NOI18N
        onlineModeToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineModeToggleActionPerformed(evt);
            }
        });

        bookingStatusPanel.setFocusable(false);
        bookingStatusPanel.setName("bookingStatusPanel"); // NOI18N

        totalLabel.setText(resourceMap.getString("totalLabel.text")); // NOI18N
        totalLabel.setFocusable(false);
        totalLabel.setName("totalLabel"); // NOI18N

        entrySlotDisplayTextField1.setEditable(false);
        entrySlotDisplayTextField1.setFocusable(false);
        entrySlotDisplayTextField1.setFont(resourceMap.getFont("entrySlotDisplayTextField1.font")); // NOI18N
        entrySlotDisplayTextField1.setName("entrySlotDisplayTextField1"); // NOI18N
        entrySlotDisplayTextField1.setRequestFocusEnabled(false);

        attendeeCountLabel1.setText(resourceMap.getString("attendeeCountLabel1.text")); // NOI18N
        attendeeCountLabel1.setFocusable(false);
        attendeeCountLabel1.setName("attendeeCountLabel1"); // NOI18N

        statusDisplayTextField1.setEditable(false);
        statusDisplayTextField1.setFocusable(false);
        statusDisplayTextField1.setFont(resourceMap.getFont("statusDisplayTextField1.font")); // NOI18N
        statusDisplayTextField1.setName("statusDisplayTextField1"); // NOI18N

        attendeeCountLabel2.setText(resourceMap.getString("attendeeCountLabel2.text")); // NOI18N
        attendeeCountLabel2.setFocusable(false);
        attendeeCountLabel2.setName("attendeeCountLabel2"); // NOI18N

        localAttendeeCountTextField.setEditable(false);
        localAttendeeCountTextField.setText(resourceMap.getString("localAttendeeCountTextField.text")); // NOI18N
        localAttendeeCountTextField.setFocusable(false);
        localAttendeeCountTextField.setFont(resourceMap.getFont("localAttendeeCountTextField.font")); // NOI18N
        localAttendeeCountTextField.setName("localAttendeeCountTextField"); // NOI18N
        localAttendeeCountTextField.setRequestFocusEnabled(false);

        statusLabel1.setText(resourceMap.getString("statusLabel1.text")); // NOI18N
        statusLabel1.setFocusable(false);
        statusLabel1.setName("statusLabel1"); // NOI18N

        entrySlotLabel1.setText(resourceMap.getString("entrySlotLabel1.text")); // NOI18N
        entrySlotLabel1.setFocusable(false);
        entrySlotLabel1.setName("entrySlotLabel1"); // NOI18N

        thisMachineLabel.setText(resourceMap.getString("thisMachineLabel.text")); // NOI18N
        thisMachineLabel.setFocusable(false);
        thisMachineLabel.setName("thisMachineLabel"); // NOI18N

        totalAttendeeCountDisplay.setEditable(false);
        totalAttendeeCountDisplay.setText(resourceMap.getString("totalAttendeeCountDisplay.text")); // NOI18N
        totalAttendeeCountDisplay.setFocusable(false);
        totalAttendeeCountDisplay.setFont(resourceMap.getFont("totalAttendeeCountDisplay.font")); // NOI18N
        totalAttendeeCountDisplay.setName("totalAttendeeCountDisplay"); // NOI18N
        totalAttendeeCountDisplay.setRequestFocusEnabled(false);

        refreshAttendeesButton.setIcon(resourceMap.getIcon("refreshAttendeesButton.icon")); // NOI18N
        refreshAttendeesButton.setText(resourceMap.getString("refreshAttendeesButton.text")); // NOI18N
        refreshAttendeesButton.setFocusable(false);
        refreshAttendeesButton.setName("refreshAttendeesButton"); // NOI18N
        refreshAttendeesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshAttendeesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookingStatusPanelLayout = new javax.swing.GroupLayout(bookingStatusPanel);
        bookingStatusPanel.setLayout(bookingStatusPanelLayout);
        bookingStatusPanelLayout.setHorizontalGroup(
            bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                .addComponent(statusLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusDisplayTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entrySlotLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrySlotDisplayTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(attendeeCountLabel1))
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(thisMachineLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localAttendeeCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(totalLabel))
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(attendeeCountLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalAttendeeCountDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refreshAttendeesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        bookingStatusPanelLayout.setVerticalGroup(
            bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusLabel1)
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusDisplayTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entrySlotDisplayTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(localAttendeeCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thisMachineLabel)))
                    .addComponent(entrySlotLabel1)
                    .addComponent(attendeeCountLabel1)
                    .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                            .addComponent(attendeeCountLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(totalLabel))
                        .addComponent(totalAttendeeCountDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(refreshAttendeesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainOnlinePanelLayout = new javax.swing.GroupLayout(mainOnlinePanel);
        mainOnlinePanel.setLayout(mainOnlinePanelLayout);
        mainOnlinePanelLayout.setHorizontalGroup(
            mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainOnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bookingStatusScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainOnlinePanelLayout.createSequentialGroup()
                        .addComponent(backButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 534, Short.MAX_VALUE)
                        .addComponent(finishButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainOnlinePanelLayout.createSequentialGroup()
                        .addComponent(searchInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchInput, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkingModeToggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(onlineModeToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bookingStatusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainOnlinePanelLayout.setVerticalGroup(
            mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainOnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlineModeToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchInputLabel)
                    .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchInput, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addComponent(checkingModeToggle1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingStatusScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backButton1)
                    .addComponent(finishButton))
                .addContainerGap())
        );

        searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
            java.awt.event.InputEvent.CTRL_DOWN_MASK), "save");
    searchInput.getActionMap().put("save", save);

    searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        java.awt.event.InputEvent.CTRL_DOWN_MASK), "toggleBooking");
searchInput.getActionMap().put("toggleBooking", toggleBooking);

searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T,
    java.awt.event.InputEvent.CTRL_DOWN_MASK), "toggleConnection");
    searchInput.getActionMap().put("toggleConnection", toggleConnection);

    searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "enterStudentNumber");
    searchInput.getActionMap().put("enterStudentNumber",checkBooking);

    searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
        "refreshAttendees");
    searchInput.getActionMap().put("refreshAttendees",refreshAttendees);

    finishPanel.setMinimumSize(new java.awt.Dimension(720, 350));
    finishPanel.setName("finishPanel"); // NOI18N

    finishCloseButton.setAction(actionMap.get("finishAction")); // NOI18N
    finishCloseButton.setText(resourceMap.getString("finishCloseButton.text")); // NOI18N
    finishCloseButton.setName("finishCloseButton"); // NOI18N

    finishBackButton.setText(resourceMap.getString("finishBackButton.text")); // NOI18N
    finishBackButton.setName("finishBackButton"); // NOI18N
    finishBackButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            finishBackButtonActionPerformed(evt);
        }
    });

    finishPanelTitle.setFont(resourceMap.getFont("finishPanelTitle.font")); // NOI18N
    finishPanelTitle.setText(resourceMap.getString("finishPanelTitle.text")); // NOI18N
    finishPanelTitle.setName("finishPanelTitle"); // NOI18N

    markAbsentOption.setFont(resourceMap.getFont("markAbsentOption.font")); // NOI18N
    markAbsentOption.setSelected(true);
    markAbsentOption.setText(resourceMap.getString("markAbsentOption.text")); // NOI18N
    markAbsentOption.setName("markAbsentOption"); // NOI18N
    markAbsentOption.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            markAbsentOptionActionPerformed(evt);
        }
    });

    notifyAbsentOption.setFont(resourceMap.getFont("notifyAbsentOption.font")); // NOI18N
    notifyAbsentOption.setSelected(true);
    notifyAbsentOption.setText(resourceMap.getString("notifyAbsentOption.text")); // NOI18N
    notifyAbsentOption.setName("notifyAbsentOption"); // NOI18N

    javax.swing.GroupLayout finishPanelLayout = new javax.swing.GroupLayout(finishPanel);
    finishPanel.setLayout(finishPanelLayout);
    finishPanelLayout.setHorizontalGroup(
        finishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(finishPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(finishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(finishPanelLayout.createSequentialGroup()
                    .addComponent(markAbsentOption)
                    .addContainerGap())
                .addGroup(finishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(finishPanelLayout.createSequentialGroup()
                        .addComponent(notifyAbsentOption)
                        .addContainerGap())
                    .addGroup(finishPanelLayout.createSequentialGroup()
                        .addComponent(finishBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                        .addComponent(finishCloseButton)
                        .addGap(20, 20, 20))
                    .addGroup(finishPanelLayout.createSequentialGroup()
                        .addComponent(finishPanelTitle)
                        .addContainerGap(460, Short.MAX_VALUE)))))
    );
    finishPanelLayout.setVerticalGroup(
        finishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, finishPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(finishPanelTitle)
            .addGap(41, 41, 41)
            .addComponent(markAbsentOption)
            .addGap(18, 18, 18)
            .addComponent(notifyAbsentOption)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
            .addGroup(finishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(finishBackButton)
                .addComponent(finishCloseButton))
            .addContainerGap())
    );

    setComponent(preConfigPanel);
    setMenuBar(menuBar);
    addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            formPropertyChange(evt);
        }
    });
    }// </editor-fold>//GEN-END:initComponents
    
    private void toggleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleMenuItemActionPerformed
        checkingModeToggle1.doClick();
    }//GEN-LAST:event_toggleMenuItemActionPerformed

    private void okConfigButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okConfigButton1ActionPerformed
        checkOnlineConfiguration();
    }//GEN-LAST:event_okConfigButton1ActionPerformed

    private void entrySlotsSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_entrySlotsSpinner1StateChanged
        updateOnlineBookingPanel(true);
    }//GEN-LAST:event_entrySlotsSpinner1StateChanged

private void inputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputFocusLost
    JFormattedTextField input = (JFormattedTextField) evt.getSource();
    boolean isFileInput = (input == entrySlotBookingListFilePathInput1 ||
                           input == entrySlotBookingListFilePathInput2 ||
                           input == entrySlotBookingListFilePathInput3 ||
                           input == entrySlotBookingListFilePathInput4 ||
                           input == entrySlotBookingListFilePathInput5 ||
                           input == waitingListFilePathInput);
    boolean isIdInput = (input == entrySlotIdInput1 ||
                         input == entrySlotIdInput2 ||
                         input == entrySlotIdInput3 ||
                         input == entrySlotIdInput4 ||
                         input == entrySlotIdInput5);
    if (input == eventTitleInput && input.getText().equals(""))
        input.setText(titleInputDefault);
    else if (isFileInput && input.getText().equals(""))
        input.setText(fileInputDefault);
    else if (isIdInput && input.getText().equals(""))
        input.setText(idInputDefault);
}//GEN-LAST:event_inputFocusLost

private void inputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputFocusGained
    JFormattedTextField input = (JFormattedTextField) evt.getSource();
    boolean isFileInput = (input == entrySlotBookingListFilePathInput1 ||
                           input == entrySlotBookingListFilePathInput2 ||
                           input == entrySlotBookingListFilePathInput3 ||
                           input == entrySlotBookingListFilePathInput4 ||
                           input == entrySlotBookingListFilePathInput5 ||
                           input == waitingListFilePathInput);
    boolean isIdInput = (input == entrySlotIdInput1 ||
                         input == entrySlotIdInput2 ||
                         input == entrySlotIdInput3 ||
                         input == entrySlotIdInput4 ||
                         input == entrySlotIdInput5);
    if (input == eventTitleInput && input.getText().equals(titleInputDefault))
        input.setText("");
    else if (isFileInput && input.getText().equals(fileInputDefault))
        input.setText("");
    else if (isIdInput && input.getText().equals(idInputDefault))
        input.setText("");
}//GEN-LAST:event_inputFocusGained

private void yesWaitingListRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesWaitingListRadioButtonActionPerformed
    updateBookingPanel(true);
}//GEN-LAST:event_yesWaitingListRadioButtonActionPerformed

private void noWaitingListRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noWaitingListRadioButtonActionPerformed
    updateBookingPanel(true);
}//GEN-LAST:event_noWaitingListRadioButtonActionPerformed

private void browseFileAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileAction
    JFileChooser fc = new JFileChooser();
    File file = null;
    fc.addChoosableFileFilter(new TextCSVFilter());
    int returnVal = fc.showDialog(app.getMainFrame(), "Choose");
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        file = fc.getSelectedFile();
        JButton source = (JButton) evt.getSource();
        JFormattedTextField pathInput;
        if (source == bookingListBrowseButton1) {
            pathInput = entrySlotBookingListFilePathInput1;
        }
        else if (source == bookingListBrowseButton2) {
            pathInput = entrySlotBookingListFilePathInput2;
        }
        else if (source == bookingListBrowseButton3) {
            pathInput = entrySlotBookingListFilePathInput3;
        }
        else if (source == bookingListBrowseButton4) {
            pathInput = entrySlotBookingListFilePathInput4;
        }
        else if (source == bookingListBrowseButton5) {
            pathInput = entrySlotBookingListFilePathInput5;
        }
        else if (source == waitingListBrowseButton) {
            pathInput = waitingListFilePathInput;
        }
        else {
            return;
        }
        pathInput.setText(file.getPath());
    }
}//GEN-LAST:event_browseFileAction

private void entrySlotsSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_entrySlotsSpinnerStateChanged
    updateBookingPanel(true);
}//GEN-LAST:event_entrySlotsSpinnerStateChanged

private void okConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okConfigButtonActionPerformed
    checkConfiguration();
}//GEN-LAST:event_okConfigButtonActionPerformed

private void noBookingRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noBookingRadioButtonActionPerformed
    updateBookingPanel(yesBookingRadioButton.isSelected());
}//GEN-LAST:event_noBookingRadioButtonActionPerformed

private void yesBookingRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesBookingRadioButtonActionPerformed
    updateBookingPanel(yesBookingRadioButton.isSelected());
}//GEN-LAST:event_yesBookingRadioButtonActionPerformed

private void loadEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadEventButtonActionPerformed
    String id = "";
    JTextField titleInput;
    JButton source = (JButton) evt.getSource();
    if (source == loadEventButton1) {
        id = entrySlotIdInput1.getText();
        titleInput = generatedTitle1;
    }
    else if(source == loadEventButton2) {
        id = entrySlotIdInput2.getText();
        titleInput = generatedTitle2;
    }
    else if(source == loadEventButton3) {
        id = entrySlotIdInput3.getText();
        titleInput = generatedTitle3;
    }
    else if(source == loadEventButton4) {
        id = entrySlotIdInput4.getText();
        titleInput = generatedTitle4;
    }
    else if(source == loadEventButton5) {
        id = entrySlotIdInput5.getText();
        titleInput = generatedTitle5;
    }
    else {
        return;
    }

    if (id.isEmpty()) {
        JOptionPane.showMessageDialog(app.getMainFrame(),
                                      "You have not entered an entry slot ID!",
                                      "Event ID error",
                                      JOptionPane.ERROR_MESSAGE);
    }
    else {
        Event event;
        try {
            event = app.getEvent(id);
            titleInput.setText(event.getTitle());
        } catch (org.jsoup.HttpStatusException ex) {
            Logger.getLogger(EventSwipeView.class.getName()).log(Level.WARNING, null, ex);
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "No event with this ID was found!",
                                          "Event ID error",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
            showGenericErrorMessage();
        }
    }
}//GEN-LAST:event_loadEventButtonActionPerformed

private void searchEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchEventsButtonActionPerformed
    List<Event> events = new ArrayList<Event>();
    try {
        events = app.getEvents("");
    } catch (Exception ex) {
        Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
        showGenericErrorMessage();
    }
    List<String> eventTitles = new ArrayList<String>();
    for (Event event : events){
        eventTitles.add(event.getTitle());
    }
    JList eventList = new JList(eventTitles.toArray());
    eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    eventList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    eventList.setVisibleRowCount(-1);
    JScrollPane eventListScroller = new JScrollPane(eventList);
    eventListScroller.setPreferredSize(new Dimension(500, 80));
    JOptionPane.showMessageDialog(null, eventListScroller, "Select event", JOptionPane.PLAIN_MESSAGE);
    int i = eventList.getSelectedIndex();
    if (i != -1) {
        JButton source = (JButton) evt.getSource();
        JFormattedTextField idInput;
        JTextField title;
        if (source == searchEventsButton1) {
            idInput = entrySlotIdInput1;
            title = generatedTitle1;
        }
        else if (source == searchEventsButton2) {
            idInput = entrySlotIdInput2;
            title = generatedTitle2;
        }
        else if (source == searchEventsButton3) {
            idInput = entrySlotIdInput3;
            title = generatedTitle3;
        }
        else if (source == searchEventsButton4) {
            idInput = entrySlotIdInput4;
            title = generatedTitle4;
        }
        else if (source == searchEventsButton5) {
            idInput = entrySlotIdInput5;
            title = generatedTitle5;
        }
        else {
            return;
        }
        idInput.setText(events.get(i).getId());
        title.setText(events.get(i).getTitle());
    }
}//GEN-LAST:event_searchEventsButtonActionPerformed

private void onlineConfigBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineConfigBackButtonActionPerformed
    switchToPanel(preConfigPanel);
}//GEN-LAST:event_onlineConfigBackButtonActionPerformed

private void configBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configBackButtonActionPerformed
    switchToPanel(preConfigPanel);
}//GEN-LAST:event_configBackButtonActionPerformed

private void backButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButton1ActionPerformed
    if (app.isOnlineMode())
        switchToPanel(onlineConfigPanel);
    else
        switchToPanel(configPanel);
}//GEN-LAST:event_backButton1ActionPerformed

private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
    searchInput.setEnabled(false);
    String input = searchInput.getText();
    if (!input.isEmpty()) {
        if (Utils.isNumeric(input)) {
            Booking booking = new Booking("");
                try {
                    booking = app.processSearchInput(input);
                    updateBookingStatus(booking);
                } catch (EventFullException ef) {
                    eventFullDisplay(ef.getStuNum());
                } catch (EarlyRegistrationException er) {
                    earlyRegistrationDisplay(input);
                } catch (Exception ex) {
                    Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                    showGenericErrorMessage();
                }
                finally {
                    searchInput.setText("");
                    searchInput.requestFocusInWindow();
                }
        }
        else {
            List<Student> students = new ArrayList<Student>();
            try {
                input = URLEncoder.encode(input, app.getCharset());
                students = app.getStudents(input);
            } catch (Exception ex) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                searchInput.setEnabled(true);
                showGenericErrorMessage();
            }
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(app.getMainFrame(),
                                              "No students could be found.",
                                              "Search error",
                                              JOptionPane.ERROR_MESSAGE);
            }
            else if (app.isOnlineMode()) {
                List<String> studentNames = new ArrayList<String>();
                for (Student student : students){
                    studentNames.add(student.getFirstName()+" "+student.getLastName());
                }
                JList studentList = new JList(studentNames.toArray());
                studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                studentList.setLayoutOrientation(JList.VERTICAL_WRAP);
                studentList.setVisibleRowCount(-1);
                JScrollPane studentListScroller = new JScrollPane(studentList);
                studentListScroller.setPreferredSize(new Dimension(500, 80));
                JOptionPane.showMessageDialog(null, studentListScroller, "Select student",
                                              JOptionPane.PLAIN_MESSAGE);
                int i = studentList.getSelectedIndex();
                if (i != -1) {
                    String stuNum = students.get(i).getStuNumber();
                    if (stuNum.equals(app.getEmptyStuNumString())) {
                        JOptionPane.showMessageDialog(app.getMainFrame(),
                                                      "Attendee has no student number.",
                                                      "Student number error",
                                                      JOptionPane.ERROR_MESSAGE);
                        searchInput.setText("");
                        searchInput.requestFocusInWindow();
                    }
                    else {
                        searchInput.setText(students.get(i).getStuNumber());
                        searchButton.doClick();
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(app.getMainFrame(),
                                              "Not a valid student number!",
                                              "Student number error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    searchInput.setEnabled(true);
    
}//GEN-LAST:event_searchButtonActionPerformed

private void checkingModeToggle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkingModeToggle1ActionPerformed
    toggleCheckingMode();
}//GEN-LAST:event_checkingModeToggle1ActionPerformed

private void onlineModeToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineModeToggleActionPerformed
    toggleOnlineMode();
}//GEN-LAST:event_onlineModeToggleActionPerformed

private void startOfflineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startOfflineButtonActionPerformed
    app.setOnlineModeFlag(false);
    switchToPanel(configPanel);
}//GEN-LAST:event_startOfflineButtonActionPerformed

private void loginInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginInputKeyPressed
    KeyEvent ke = (KeyEvent) evt;
    if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
        logIn();
    }
}//GEN-LAST:event_loginInputKeyPressed

private void idInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idInputKeyPressed
    KeyEvent ke = (KeyEvent) evt;
    if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
        JButton target;
        JFormattedTextField source = (JFormattedTextField) evt.getSource();
        if (source == entrySlotIdInput1) {
           target = loadEventButton1;
        }
        else if(source == entrySlotIdInput2) {
           target = loadEventButton2;
        }
        else if(source == entrySlotIdInput3) {
           target = loadEventButton3;
        }
        else if(source == entrySlotIdInput4) {
           target = loadEventButton4;
        }
        else if(source == entrySlotIdInput5) {
           target = loadEventButton5;
        }
        else {
            return;
        }
        target.doClick();
    }
}//GEN-LAST:event_idInputKeyPressed

private void markAbsentOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markAbsentOptionActionPerformed
    notifyAbsentOption.setEnabled(markAbsentOption.isSelected());
}//GEN-LAST:event_markAbsentOptionActionPerformed

private void finishBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBackButtonActionPerformed
    this.switchToPanel(mainOnlinePanel);
}//GEN-LAST:event_finishBackButtonActionPerformed

private void connectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionMenuItemActionPerformed
    onlineModeToggle.doClick();
}//GEN-LAST:event_connectionMenuItemActionPerformed

private void refreshAttendeesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshAttendeesButtonActionPerformed
    String currentCount = totalAttendeeCountDisplay.getText();
    try {
        totalAttendeeCountDisplay.setText(app.getAttendeeCount());
    } catch (Exception ex) {
        Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
        totalAttendeeCountDisplay.setText(currentCount);
    }
}//GEN-LAST:event_refreshAttendeesButtonActionPerformed

private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
    // TODO add your handling code here:
}//GEN-LAST:event_formPropertyChange
    
    private void checkConfiguration() {
        app.clearData();
        int slots = (Integer) entrySlotsSpinner.getValue();
        boolean booking = yesBookingRadioButton.isSelected();
        boolean waitingList = yesWaitingListRadioButton.isSelected();
        app.setBookingFlag(booking);
        app.setWaitingListFlag(waitingList);
        app.setSlots(slots);
        boolean configOK = false;
        if (eventTitleInput.getText().equals(titleInputDefault)) {
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "You have not entered an event title. Please write one!",
                                          "Event title error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        else {
            app.setEventTitle(eventTitleInput.getText());
            configOK = true;
        }

        if (booking) {
            List<String> filePaths = new ArrayList<String>();
            switch (slots) {
                case 3:
                   filePaths.add(entrySlotBookingListFilePathInput3.getText());
                case 2:
                   filePaths.add(entrySlotBookingListFilePathInput2.getText());
                case 1:
                   filePaths.add(entrySlotBookingListFilePathInput1.getText());
                default:
                   break;
            }
            app.setEvents(filePaths);
        }
        else {
            Event event = new Event();
            event.setSlot(1);
            event.setTitle(eventTitleInput.getText());
            app.addEvent(event);
        }

        if (waitingList) {
            app.createWaitingList(waitingListFilePathInput.getText());
        }

        if (configOK) {
            app.createLog();
            onlineModeToggle.setEnabled(false);
            totalAttendeeCountDisplay.setEnabled(false);
            switchToPanel(mainOnlinePanel);
        }
    }

    private void checkOnlineConfiguration() {
        app.clearData();
        int slots = (Integer) entrySlotsSpinner1.getValue();
        boolean useWaitingList = yesLoadWaitingListRadioButton.isSelected();
        app.setWaitingListFlag(false);
        app.setSlots(slots);
        boolean configOK = false;
        String displayTitle = "";

        javax.swing.JTextField[] idArray = {entrySlotIdInput1, entrySlotIdInput2, entrySlotIdInput3,
                                            entrySlotIdInput4, entrySlotIdInput5};
        List<javax.swing.JTextField> idList = Arrays.asList(idArray);

        javax.swing.JTextField[] titleArray = {generatedTitle1, generatedTitle2, generatedTitle3,
                                               generatedTitle4, generatedTitle5};
        List<javax.swing.JTextField> titleList = Arrays.asList(titleArray);

        for (int i = 0; i < slots; i++) {
            String id = idList.get(i).getText();
            if (!(id.equals(idInputDefault) || id.equals(""))) {
                try {
                    Event slot = app.loadEvent(id, i + 1, useWaitingList);
                    int bookingCount = slot.getBookingList().size(),
                        bookingLimit = slot.getBookingLimit();
                    if (slot.isDropIn()) {
                        Object[] options = {"Go to event", "Cancel"};
                        int reply = JOptionPane.showOptionDialog(app.getMainFrame(),
                                                              slot.getTitle() + " does not have booking enabled. " +
                                                              "You must enable booking (without a booking limit) before continuing.",
                                                              "Event booking error",
                                                              JOptionPane.YES_NO_OPTION,
                                                              JOptionPane.QUESTION_MESSAGE,
                                                              null,
                                                              options,
                                                              options[0]);
                        if (reply == JOptionPane.YES_OPTION) {
                            browseToUrl(app.getAdminEventURL(slot.getId()));
                        }
                        return;
                    }
                    else if(!slot.isUnlimited()) {
                        int spaces = bookingLimit - bookingCount;
                        String message = "";
                        Object[] options = {"Go to event", "Continue"};
                        if (spaces == 0) {
                            message = slot.getTitle() + " is fully booked. " +
                                      System.getProperty("line.separator") +
                                      "It is strongly recommended you remove the booking limit on CareerHub.";
                        }
                        else {
                            message = "You will only be able to book " +
                                      spaces + " extra " + 
                                      (spaces == 1 ? "attendee" : "attendees") +
                                      " for " + slot.getTitle() + "." +
                                      System.getProperty("line.separator") +
                                      "It is recommended you remove the booking limit on CareerHub.";
                        }
                        int reply = JOptionPane.showOptionDialog(app.getMainFrame(),
                                                              message,
                                                              "Booking limit warning",
                                                              JOptionPane.YES_NO_OPTION,
                                                              JOptionPane.QUESTION_MESSAGE,
                                                              null,
                                                              options,
                                                              options[0]);
                        if (reply == JOptionPane.YES_OPTION) {
                            browseToUrl(app.getAdminEventURL(slot.getId()));
                        }
                    }
                    String title = slot.getTitle();
                    titleList.get(i).setText(title);
                    displayTitle += " - " + title;
                    configOK = true;
                } catch (Exception ex) {
                    configOK = false;
                    Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                configOK = false;
            }
        }
        if (configOK) {
            app.setEventTitle(displayTitle.substring(3));
            app.createLog();
            app.setBookingFlag(app.getBookedCount() > 0);
            connectionMenuItem.setEnabled(true);
            onlineModeToggle.setEnabled(true);
            totalAttendeeCountDisplay.setEnabled(true);
            String totalAttendees = "0";
            try {
                totalAttendees = app.getAttendeeCount();
            } catch (Exception ex) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
            }
            totalAttendeeCountDisplay.setText(totalAttendees);
            switchToPanel(mainOnlinePanel);
        }
        else {
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "There is something wrong with your event details. " +
                                          "Please try entering them again.",
                                          "Event configuration error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBookingStatus(Booking booking) {
        String stuNumber = booking.getStuNumber();
        String message = "Student " + stuNumber;
        String slot = booking.getEntrySlot() == 0 ? "N/A" : booking.getEntrySlot().toString();
        slot = booking.isOnWaitingList() ? "W/L" : slot;
        String bookingStatus = "";
        if (booking.isAlreadyRecorded()) {
            Utils.successNoise();
            message += " has already been recorded";
            bookingStatus = "Already recorded";
        }
        else if (booking.getStatus() == Booking.EARLY_STATUS) {
            Utils.failureNoise();
            bookingStatus = "Too early";
            int reply = JOptionPane.showConfirmDialog(app.getMainFrame(),
                                                      "Student has arrived too early to register. "
                                                      + "Allow student to enter?",
                                                      "Student too early",
                                                      JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                app.addToEarlyList(stuNumber, booking.getEntrySlot());
                bookingStatus = "Booked";
                message += " has been recorded";
            }
            else {
                message += " has arrived too early";
            }
        }
        else if(booking.isOnWaitingList()) {
            Utils.failureNoise();
            Utils.pressAlt();
            int reply = JOptionPane.showConfirmDialog(app.getMainFrame(),
                                                      "Student is on the waiting list. "
                                                      + "Allow student to enter?",
                                                      "Student on waiting list",
                                                      JOptionPane.YES_NO_OPTION);
            Utils.releaseAlt();
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    app.recordAttendance(app.bookStudent(stuNumber, new Booking(stuNumber)));
                    bookingStatus = "Recorded";
                    message += " has been recorded";
                } catch (EventFullException ef) {
                    eventFullDisplay(ef.getStuNum());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                    showGenericErrorMessage();
                }
            }
            else {
                bookingStatus = "Waiting list";
                message += " is on the waiting list";
            }
        }
        else if (booking.isBooked()) {
            Utils.successNoise();
            if (app.getBookingFlag()) {
                bookingStatus = "Booked";
                message += " has booked";
                if (app.getSlots() > 1) {
                    message += " for entry slot " + slot;
                }
            }
            else {
                bookingStatus = "Recorded";
                message += " has been recorded";
                
            }
        }
        else { //not booked
            Utils.failureNoise();
            Utils.pressAlt();
            int reply = JOptionPane.showConfirmDialog(app.getMainFrame(),
                                                      "Student is not booked. " +
                                                      "Allow student to enter?",
                                                      "Student not booked",
                                                      JOptionPane.YES_NO_OPTION);
            Utils.releaseAlt();
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    app.recordAttendance(app.bookStudent(stuNumber, new Booking(stuNumber)));
                    bookingStatus = "Recorded";
                    message += " has been recorded";
                } catch (NoStudentFoundException nsf) {
                    JOptionPane.showMessageDialog(app.getMainFrame(),
                                                  "Student " + nsf.getStuNum() +
                                                  " could not be found in CareerHub.",
                                                  "No student found",
                                                  JOptionPane.ERROR_MESSAGE);
                    message += " could not be found in CareerHub";
                    bookingStatus = "Not booked";
                } catch (EventFullException ef) {
                    eventFullDisplay(ef.getStuNum());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                    showGenericErrorMessage();
                }
            }
            else {
                message += " has NOT booked onto this event";
                bookingStatus = "Not booked";
            }
        }
        displayBookingStatus(bookingStatus, slot);
        displayBookingMessage(message);
        app.log(message);
    }

    private void displayBookingMessage(String message) {
        message += "\n";
        bookingStatusTextArea1.append(message);
        searchInput.setText("");
        searchInput.requestFocusInWindow();
    }

    private void displayBookingStatus(String statusMessage, String slot) {
        statusDisplayTextField1.setText(statusMessage);
        entrySlotDisplayTextField1.setText(slot);
        Color color;
        boolean enabled;
        if(statusMessage.equals("Booked")) {
            color = Color.GREEN;
            enabled = true;
            localAttendeeCountTextField.setText(app.getLocalAttendeeCount());        
        }
        else if(statusMessage.equals("Recorded") || statusMessage.equals("")) {
            color = Color.WHITE;
            enabled = false;
            localAttendeeCountTextField.setText(app.getLocalAttendeeCount());
        }
        else {
            enabled = false;
            color = statusMessage.equals("Not booked") ? Color.RED : Color.ORANGE;
        }
        statusDisplayTextField1.setBackground(color);
        entrySlotLabel1.setEnabled(enabled);
        entrySlotDisplayTextField1.setEnabled(enabled);
        int delay = 250; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                statusDisplayTextField1.setBackground(null);
            }
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false);
        timer.start();
        if (app.isOnlineMode()){
            try {
                totalAttendeeCountDisplay.setText(app.getAttendeeCount());
            } catch (Exception ex) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                totalAttendeeCountDisplay.setText(app.getLocalAttendeeCount());
                //showGenericErrorMessage();
            }
        }
    }

    private void eventFullDisplay(String stuNumber) {
        Utils.failureNoise();
        String message = "Student " + stuNumber + " couldn't be booked because the event is full";
        String bookingStatus = "EVENT FULL";
        String slot = "N/A";
        displayBookingStatus(bookingStatus, slot);
        displayBookingMessage(message);
        app.log(message);
        Object[] options = {"Go to event", "Cancel"};
        int reply = JOptionPane.showOptionDialog(app.getMainFrame(),
                                                 "There are no free spaces to book this student. " +
                                                 "Remove the booking limit on one of the entry slots before continuing.",
                                                 "Event full error",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 null,
                                                 options,
                                                 options[0]);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                browseToUrl(app.getAdminEventURL(app.getEvent(0).getId()));
            } catch (IOException ex) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                showGenericErrorMessage();
            }
        }
    }

    private void earlyRegistrationDisplay(String stuNumber) {
        Utils.failureNoise();
        String message = "Student " + stuNumber + " is trying to register too early";
        String bookingStatus = "TOO EARLY";
        String slot = "N/A";
        displayBookingStatus(bookingStatus, slot);
        displayBookingMessage(message);
        app.log(message);
        JOptionPane.showMessageDialog(app.getMainFrame(),
                                          message,
                                          "Too early to register",
                                          JOptionPane.ERROR_MESSAGE);
    }

    private void switchToPanel(JPanel panel) {
        JFrame mainFrame = app.getMainFrame();
        mainFrame.setContentPane(panel);
        if (panel == mainOnlinePanel) {
            boolean online = app.isOnlineMode();
            boolean booking = app.getBookingFlag();
            searchInput.requestFocusInWindow();
            onlineModeToggle.setSelected(online);
            onlineModeToggle.setToolTipText(online ? onlineModeTooltipText : 
                                                     offlineModeTooltipText);
            checkingModeToggle1.setEnabled(booking);
            checkingModeToggle1.setSelected(booking);
            checkingModeToggle1.setText(booking ? checkingListsText :
                                                  recordingAllText);
            toggleMenuItem.setEnabled(booking);
        }
        panel.revalidate();
        mainFrame.repaint();
    }

    private void updateBookingPanel(boolean enabled) {
        Integer slots = (Integer)entrySlotsSpinner.getValue();
        bookingDetailsPanel.setEnabled(enabled);

        bookingListBrowseButton1.setEnabled(enabled);
        bookingListBrowseButton2.setEnabled(enabled && slots > 1);
        bookingListBrowseButton3.setEnabled(enabled && slots > 2);
        bookingListBrowseButton4.setEnabled(enabled && slots > 3);
        bookingListBrowseButton5.setEnabled(enabled && slots > 4);
        waitingListBrowseButton.setEnabled(enabled &&
                                           yesWaitingListRadioButton.isSelected());

        entrySlotBookingListFilePathInput1.setEnabled(enabled);
        entrySlotBookingListFilePathInput2.setEnabled(enabled && slots > 1);
        entrySlotBookingListFilePathInput3.setEnabled(enabled && slots > 2);
        entrySlotBookingListFilePathInput4.setEnabled(enabled && slots > 3);
        entrySlotBookingListFilePathInput5.setEnabled(enabled && slots > 4);
        waitingListFilePathInput.setEnabled(enabled &&
                                            yesWaitingListRadioButton.isSelected());

        entrySlotBookingListLabel1.setEnabled(enabled);
        entrySlotBookingListLabel2.setEnabled(enabled && slots > 1);
        entrySlotBookingListLabel3.setEnabled(enabled && slots > 2);
        entrySlotBookingListLabel4.setEnabled(enabled && slots > 3);
        entrySlotBookingListLabel5.setEnabled(enabled && slots > 4);
        waitingListFileLabel.setEnabled(enabled &&
                             yesWaitingListRadioButton.isSelected());

        entrySlotsLabel.setEnabled(enabled);
        entrySlotsSpinner.setEnabled(enabled);
        noWaitingListRadioButton.setEnabled(enabled);
        yesWaitingListRadioButton.setEnabled(enabled);
        waitingListLabel.setEnabled(enabled);
        
        entrySlotLabel1.setEnabled(enabled);
        entrySlotDisplayTextField1.setText(enabled ? "": "N/A");
        entrySlotDisplayTextField1.setEnabled(enabled);
        checkingModeToggle1.setEnabled(enabled);
        checkingModeToggle1.setText(enabled ? checkingListsText :
                                              recordingAllText);
        toggleMenuItem.setEnabled(enabled);
    }

    private void updateOnlineBookingPanel(boolean enabled) {
        Integer slots = (Integer)entrySlotsSpinner1.getValue();
        bookingDetailsPanel.setEnabled(enabled);

        loadEventButton1.setEnabled(enabled);
        entrySlotIdInput1.setEnabled(enabled);
        searchEventsButton1.setEnabled(enabled);
        slot1Label.setText(slots > 1 ? "Slot one" : "Event details");

        loadEventButton2.setEnabled(enabled && slots > 1);
        entrySlotIdInput2.setEnabled(enabled && slots > 1);
        searchEventsButton2.setEnabled(enabled && slots > 1);
        slot2Label.setEnabled(enabled && slots > 1);

        loadEventButton3.setEnabled(enabled && slots > 2);
        entrySlotIdInput3.setEnabled(enabled && slots > 2);
        searchEventsButton3.setEnabled(enabled && slots > 2);
        slot3Label.setEnabled(enabled && slots > 2);

        loadEventButton4.setEnabled(enabled && slots > 3);
        entrySlotIdInput4.setEnabled(enabled && slots > 3);
        searchEventsButton4.setEnabled(enabled && slots > 3);
        slot4Label.setEnabled(enabled && slots > 3);

        loadEventButton5.setEnabled(enabled && slots > 4);
        entrySlotIdInput5.setEnabled(enabled && slots > 4);
        searchEventsButton5.setEnabled(enabled && slots > 4);
        slot5Label.setEnabled(enabled && slots > 4);

        checkingModeToggle1.setEnabled(enabled);
        checkingModeToggle1.setText(enabled ? checkingListsText :
                                              recordingAllText);
        toggleMenuItem.setEnabled(enabled);
    }


    private void toggleCheckingMode() {
        if (checkingModeToggle1.isSelected()) {
            app.setBookingFlag(true);
            checkingModeToggle1.setText(checkingListsText);
        }
        else {
            app.setBookingFlag(false);
            checkingModeToggle1.setText(recordingAllText);
        }
    }

    private void toggleOnlineMode() {
        if (onlineModeToggle.isSelected()) {
            try {
                app.goToOnlineMode();
                String attendees = app.getAttendeeCount();
                totalAttendeeCountDisplay.setText(attendees);
            } catch (EventFullException ef) {
                eventFullDisplay(ef.getStuNum());
            } catch (IOException ioe) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ioe);
                JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Can't connect to the internet.",
                                          "Connection error",
                                          JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                showGenericErrorMessage();
            }
            totalAttendeeCountDisplay.setEnabled(true);
            onlineModeToggle.setToolTipText(onlineModeTooltipText);
        }
        else {
            app.setOnlineModeFlag(false);
            totalAttendeeCountDisplay.setEnabled(false);
            onlineModeToggle.setToolTipText(offlineModeTooltipText);
        }
    }

    private void browseToUrl(String url) {
        if(!Desktop.isDesktopSupported()) {
            System.err.println("Desktop is not supported");
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Can't open browser. Edit event manually.",
                                          "Browser error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        else {
            Desktop desktop = Desktop.getDesktop();
            if(!desktop.isSupported(Desktop.Action.BROWSE)) {
                System.err.println("Desktop doesn't support the browse action");
                JOptionPane.showMessageDialog(app.getMainFrame(),
                                              "Can't open browser. Edit event manually.",
                                              "Browser error",
                                              JOptionPane.ERROR_MESSAGE);
            }
            else {
                URI uri = null;
                try {
                    uri = new URI(url);
                    desktop.browse(uri);
                } catch (Exception ex) {
                    Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(app.getMainFrame(),
                                              "Can't open browser. Edit event manually.",
                                              "Browser error",
                                              JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showGenericErrorMessage() {
        if (!searchInput.isEnabled()) {
            searchInput.setEnabled(true);
        }
        JOptionPane.showMessageDialog(app.getMainFrame(),
                                      "Something has gone wrong! Close EventSwipe and log in again.",
                                      "Unexpected error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aboutEventLabel;
    private javax.swing.JLabel attendeeCountLabel1;
    private javax.swing.JLabel attendeeCountLabel2;
    private javax.swing.JButton backButton1;
    private javax.swing.JPanel bookingDetailsPanel;
    private javax.swing.JPanel bookingDetailsPanel1;
    private javax.swing.JButton bookingListBrowseButton1;
    private javax.swing.JButton bookingListBrowseButton2;
    private javax.swing.JButton bookingListBrowseButton3;
    private javax.swing.JButton bookingListBrowseButton4;
    private javax.swing.JButton bookingListBrowseButton5;
    private javax.swing.JPanel bookingStatusPanel;
    private javax.swing.JScrollPane bookingStatusScrollPane1;
    private javax.swing.JTextArea bookingStatusTextArea1;
    private javax.swing.JToggleButton checkingModeToggle1;
    private javax.swing.JButton configBackButton;
    private javax.swing.JPanel configPanel;
    private javax.swing.JMenuItem connectionMenuItem;
    private javax.swing.JPanel entrySlot1Panel;
    private javax.swing.JPanel entrySlot2Panel;
    private javax.swing.JPanel entrySlot3Panel;
    private javax.swing.JPanel entrySlot4Panel;
    private javax.swing.JPanel entrySlot5Panel;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput1;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput2;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput3;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput4;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput5;
    private javax.swing.JLabel entrySlotBookingListLabel1;
    private javax.swing.JLabel entrySlotBookingListLabel2;
    private javax.swing.JLabel entrySlotBookingListLabel3;
    private javax.swing.JLabel entrySlotBookingListLabel4;
    private javax.swing.JLabel entrySlotBookingListLabel5;
    private javax.swing.JFormattedTextField entrySlotDisplayTextField1;
    private javax.swing.JFormattedTextField entrySlotIdInput1;
    private javax.swing.JFormattedTextField entrySlotIdInput2;
    private javax.swing.JFormattedTextField entrySlotIdInput3;
    private javax.swing.JFormattedTextField entrySlotIdInput4;
    private javax.swing.JFormattedTextField entrySlotIdInput5;
    private javax.swing.JLabel entrySlotIdLabel1;
    private javax.swing.JLabel entrySlotIdLabel2;
    private javax.swing.JLabel entrySlotIdLabel3;
    private javax.swing.JLabel entrySlotIdLabel4;
    private javax.swing.JLabel entrySlotIdLabel5;
    private javax.swing.JLabel entrySlotLabel1;
    private javax.swing.JLabel entrySlotsLabel;
    private javax.swing.JLabel entrySlotsLabel1;
    private javax.swing.JSpinner entrySlotsSpinner;
    private javax.swing.JSpinner entrySlotsSpinner1;
    private javax.swing.JFormattedTextField eventTitleInput;
    private javax.swing.JLabel eventTitleInputLabel;
    private javax.swing.JPanel eventTitlePanel;
    private javax.swing.JButton finishBackButton;
    private javax.swing.JButton finishButton;
    private javax.swing.JButton finishCloseButton;
    private javax.swing.JPanel finishPanel;
    private javax.swing.JLabel finishPanelTitle;
    private javax.swing.JTextField generatedTitle1;
    private javax.swing.JTextField generatedTitle2;
    private javax.swing.JTextField generatedTitle3;
    private javax.swing.JTextField generatedTitle4;
    private javax.swing.JTextField generatedTitle5;
    private javax.swing.JLabel generatedTitleLabel1;
    private javax.swing.JLabel generatedTitleLabel2;
    private javax.swing.JLabel generatedTitleLabel3;
    private javax.swing.JLabel generatedTitleLabel4;
    private javax.swing.JLabel generatedTitleLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadEventButton1;
    private javax.swing.JButton loadEventButton2;
    private javax.swing.JButton loadEventButton3;
    private javax.swing.JButton loadEventButton4;
    private javax.swing.JButton loadEventButton5;
    private javax.swing.JLabel loadWaitingListLabel;
    private javax.swing.JFormattedTextField localAttendeeCountTextField;
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JSeparator loginOfflineSeparator;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel mainOnlinePanel;
    private javax.swing.JLabel mainTitle;
    private javax.swing.JCheckBox markAbsentOption;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton noBookingRadioButton;
    private javax.swing.JRadioButton noLoadWaitingListRadioButton;
    private javax.swing.JRadioButton noWaitingListRadioButton;
    private javax.swing.JCheckBox notifyAbsentOption;
    private javax.swing.JButton okConfigButton;
    private javax.swing.JButton okConfigButton1;
    private javax.swing.JButton onlineConfigBackButton;
    private javax.swing.JPanel onlineConfigPanel;
    private javax.swing.JToggleButton onlineModeToggle;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel preConfigPanel;
    private javax.swing.JButton refreshAttendeesButton;
    private javax.swing.ButtonGroup requireBookingButtonGroup;
    private javax.swing.JLabel requireBookingLabel;
    private javax.swing.JPanel requireBookingPanel;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton searchEventsButton1;
    private javax.swing.JButton searchEventsButton2;
    private javax.swing.JButton searchEventsButton3;
    private javax.swing.JButton searchEventsButton4;
    private javax.swing.JButton searchEventsButton5;
    private javax.swing.JFormattedTextField searchInput;
    private javax.swing.JLabel searchInputLabel;
    private javax.swing.JPanel slot1DetailsPanel;
    private javax.swing.JLabel slot1Label;
    private javax.swing.JPanel slot2DetailsPanel;
    private javax.swing.JLabel slot2Label;
    private javax.swing.JPanel slot3DtailsPanel;
    private javax.swing.JLabel slot3Label;
    private javax.swing.JPanel slot4DetailsPanel;
    private javax.swing.JLabel slot4Label;
    private javax.swing.JPanel slot5DetailsPanel;
    private javax.swing.JLabel slot5Label;
    private javax.swing.JPanel slotsPanel;
    private javax.swing.JLabel smallLogoLabel;
    private javax.swing.JButton startOfflineButton;
    private javax.swing.JFormattedTextField statusDisplayTextField1;
    private javax.swing.JLabel statusLabel1;
    private javax.swing.JLabel thisMachineLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titleLoginPanel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JMenuItem toggleMenuItem;
    private javax.swing.JFormattedTextField totalAttendeeCountDisplay;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JTextArea useOfflineDescription;
    private javax.swing.JLabel useOfflineLabel;
    private javax.swing.JTextField usernameInput;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JButton waitingListBrowseButton;
    private javax.swing.ButtonGroup waitingListButtonGroup;
    private javax.swing.JLabel waitingListFileLabel;
    private javax.swing.JPanel waitingListFilePanel;
    private javax.swing.JFormattedTextField waitingListFilePathInput;
    private javax.swing.JLabel waitingListLabel;
    private javax.swing.JPanel waitingListOptionsPanel;
    private javax.swing.JPanel waitingListRadioPanel;
    private javax.swing.JRadioButton yesBookingRadioButton;
    private javax.swing.JRadioButton yesLoadWaitingListRadioButton;
    private javax.swing.JRadioButton yesWaitingListRadioButton;
    // End of variables declaration//GEN-END:variables
    private JDialog aboutBox;
    private String titleInputDefault;
    private String fileInputDefault;
    private String idInputDefault;
    private String usernameInputDefault;
    private String recordingAllText;
    private String checkingListsText;
    private String onlineModeTooltipText;
    private String offlineModeTooltipText;
    //End of manually declared variables

    private EventSwipeApp app = EventSwipeApp.getApplication();

}
