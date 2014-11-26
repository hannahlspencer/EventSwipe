/*
 * EventSwipeView.java
 */
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
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
        this.getFrame().setPreferredSize(new Dimension(750, 410));
        this.getFrame().setResizable(false);
        usernameInput.requestFocusInWindow();
    }

    javax.swing.Action save = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            EventSwipeApp.getApplication().saveAttendeesToFile();
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

    @Action
    public void finishAction() {
        Boolean markAbsent = false;
        if (app.isOnlineMode()) {
            Object[] options = {"Yes", "No"};
            Utils.pressAlt();
            int reply = JOptionPane.showOptionDialog(app.getMainFrame(),
                          "Would youm like EventSwipe to mark all the 'unspecified' students as 'absent'?",
                          "Mark absentees",
                          JOptionPane.YES_NO_OPTION,
                          JOptionPane.QUESTION_MESSAGE,
                          null,
                          options,
                          options[0]);
            Utils.releaseAlt();
            if (reply == JOptionPane.YES_OPTION) {
                markAbsent = true;
            }
        }
        try {
            app.finish(markAbsent);
        } catch (Exception ex) {
            Logger.getLogger(EventSwipeView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(app.getMainFrame(),
                                          "Error marking absentees. You'll have to do this manually.",
                                          "Marking absentee error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        try {
            app.finish(false);
        } catch (Exception ex) {} //can't catch excpetion with app.finish(false)
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
        saveMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        configPanel = new javax.swing.JPanel();
        eventTitleInputLabel = new javax.swing.JLabel();
        yesBookingRadioButton = new javax.swing.JRadioButton();
        noBookingRadioButton = new javax.swing.JRadioButton();
        requireBookingLabel = new javax.swing.JLabel();
        okConfigButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        bookingDetailsPanel = new javax.swing.JPanel();
        entrySlotBookingListLabel1 = new javax.swing.JLabel();
        entrySlotsLabel = new javax.swing.JLabel();
        bookingListBrowseButton1 = new javax.swing.JButton();
        entrySlotsSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, EventSwipeData.MAX_ENTRY_SLOTS, 1));
        entrySlotBookingListFilePathInput3 = new javax.swing.JFormattedTextField();
        entrySlotBookingListFilePathInput2 = new javax.swing.JFormattedTextField();
        entrySlotBookingListLabel3 = new javax.swing.JLabel();
        entrySlotBookingListLabel2 = new javax.swing.JLabel();
        waitingListFilePathInput = new javax.swing.JFormattedTextField();
        waitingListFileLabel = new javax.swing.JLabel();
        bookingListBrowseButton2 = new javax.swing.JButton();
        bookingListBrowseButton3 = new javax.swing.JButton();
        waitingListBrowseButton = new javax.swing.JButton();
        noWaitingListRadioButton = new javax.swing.JRadioButton();
        entrySlotBookingListFilePathInput1 = new javax.swing.JFormattedTextField();
        yesWaitingListRadioButton = new javax.swing.JRadioButton();
        waitingListLabel = new javax.swing.JLabel();
        eventTitleInput = new javax.swing.JFormattedTextField();
        configBackButton = new javax.swing.JButton();
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
        okConfigButton1 = new javax.swing.JButton();
        bookingDetailsPanel1 = new javax.swing.JPanel();
        entrySlotIdLabel1 = new javax.swing.JLabel();
        entrySlotsLabel1 = new javax.swing.JLabel();
        entrySlotsSpinner1 = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, EventSwipeData.MAX_ENTRY_SLOTS, 1));
        entrySlotIdInput3 = new javax.swing.JFormattedTextField();
        entrySlotIdInput2 = new javax.swing.JFormattedTextField();
        entrySlotIdLabel3 = new javax.swing.JLabel();
        entrySlotIdLabel2 = new javax.swing.JLabel();
        noLoadWaitingListRadioButton = new javax.swing.JRadioButton();
        loadWaitingListLabel = new javax.swing.JLabel();
        entrySlotIdInput1 = new javax.swing.JFormattedTextField();
        yesLoadWaitingListRadioButton = new javax.swing.JRadioButton();
        generatedTitle1 = new javax.swing.JTextField();
        generatedTitle2 = new javax.swing.JTextField();
        generatedTitle3 = new javax.swing.JTextField();
        loadEventButton1 = new javax.swing.JButton();
        loadEventButton2 = new javax.swing.JButton();
        loadEventButton3 = new javax.swing.JButton();
        generatedTitleLabel1 = new javax.swing.JLabel();
        generatedTitleLabel2 = new javax.swing.JLabel();
        generatedTitleLabel3 = new javax.swing.JLabel();
        aboutEventLabel = new javax.swing.JLabel();
        searchEventsButton1 = new javax.swing.JButton();
        searchEventsButton2 = new javax.swing.JButton();
        searchEventsButton3 = new javax.swing.JButton();
        onlineConfigBackButton = new javax.swing.JButton();
        mainOnlinePanel = new javax.swing.JPanel();
        searchInput = new javax.swing.JFormattedTextField();
        searchButton = new javax.swing.JButton();
        bookingStatusScrollPane1 = new javax.swing.JScrollPane();
        bookingStatusTextArea1 = new javax.swing.JTextArea();
        backButton1 = new javax.swing.JButton();
        finishButton = new javax.swing.JButton();
        searchInputLabel = new javax.swing.JLabel();
        checkingModeToggle1 = new javax.swing.JToggleButton();
        generatedStudentName = new javax.swing.JTextField();
        generatedStudentLabel = new javax.swing.JLabel();
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

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(eventswipe.EventSwipeApp.class).getContext().getResourceMap(EventSwipeView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        toggleMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        toggleMenuItem.setText(resourceMap.getString("toggleMenuItem.text")); // NOI18N
        toggleMenuItem.setEnabled(false);
        toggleMenuItem.setName("toggleMenuItem"); // NOI18N
        toggleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(toggleMenuItem);

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
        configPanel.setPreferredSize(new java.awt.Dimension(720, 350));

        eventTitleInputLabel.setText(resourceMap.getString("eventTitleInputLabel.text")); // NOI18N
        eventTitleInputLabel.setName("eventTitleInputLabel"); // NOI18N

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
        bookingDetailsPanel.setName("bookingDetailsPanel"); // NOI18N

        entrySlotBookingListLabel1.setText(resourceMap.getString("entrySlotBookingListLabel1.text")); // NOI18N
        entrySlotBookingListLabel1.setName("entrySlotBookingListLabel1"); // NOI18N

        entrySlotsLabel.setText(resourceMap.getString("entrySlotsLabel.text")); // NOI18N
        entrySlotsLabel.setName("entrySlotsLabel"); // NOI18N

        bookingListBrowseButton1.setText(resourceMap.getString("bookingListBrowseButton1.text")); // NOI18N
        bookingListBrowseButton1.setName("bookingListBrowseButton1"); // NOI18N
        bookingListBrowseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        entrySlotsSpinner.setName("entrySlotsSpinner"); // NOI18N
        entrySlotsSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                entrySlotsSpinnerStateChanged(evt);
            }
        });

        entrySlotBookingListFilePathInput3.setText(fileInputDefault);
        entrySlotBookingListFilePathInput3.setName("entrySlotBookingListFilePathInput3"); // NOI18N
        entrySlotBookingListFilePathInput3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        entrySlotBookingListFilePathInput2.setText(fileInputDefault);
        entrySlotBookingListFilePathInput2.setName("entrySlotBookingListFilePathInput2"); // NOI18N
        entrySlotBookingListFilePathInput2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        entrySlotBookingListLabel3.setText(resourceMap.getString("entrySlotBookingListLabel3.text")); // NOI18N
        entrySlotBookingListLabel3.setName("entrySlotBookingListLabel3"); // NOI18N

        entrySlotBookingListLabel2.setText(resourceMap.getString("entrySlotBookingListLabel2.text")); // NOI18N
        entrySlotBookingListLabel2.setName("entrySlotBookingListLabel2"); // NOI18N

        waitingListFilePathInput.setText(fileInputDefault);
        waitingListFilePathInput.setName("waitingListFilePathInput"); // NOI18N
        waitingListFilePathInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
            }
        });

        waitingListFileLabel.setText(resourceMap.getString("waitingListFileLabel.text")); // NOI18N
        waitingListFileLabel.setName("waitingListFileLabel"); // NOI18N

        bookingListBrowseButton2.setText(resourceMap.getString("bookingListBrowseButton2.text")); // NOI18N
        bookingListBrowseButton2.setName("bookingListBrowseButton2"); // NOI18N
        bookingListBrowseButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        bookingListBrowseButton3.setText(resourceMap.getString("bookingListBrowseButton3.text")); // NOI18N
        bookingListBrowseButton3.setName("bookingListBrowseButton3"); // NOI18N
        bookingListBrowseButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        waitingListBrowseButton.setText(resourceMap.getString("waitingListBrowseButton.text")); // NOI18N
        waitingListBrowseButton.setName("waitingListBrowseButton"); // NOI18N
        waitingListBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileAction(evt);
            }
        });

        waitingListButtonGroup.add(noWaitingListRadioButton);
        noWaitingListRadioButton.setText(resourceMap.getString("noWaitingListRadioButton.text")); // NOI18N
        noWaitingListRadioButton.setName("noWaitingListRadioButton"); // NOI18N
        noWaitingListRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noWaitingListRadioButtonActionPerformed(evt);
            }
        });

        entrySlotBookingListFilePathInput1.setText(fileInputDefault);
        entrySlotBookingListFilePathInput1.setName("entrySlotBookingListFilePathInput1"); // NOI18N
        entrySlotBookingListFilePathInput1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFocusLost(evt);
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

        javax.swing.GroupLayout bookingDetailsPanelLayout = new javax.swing.GroupLayout(bookingDetailsPanel);
        bookingDetailsPanel.setLayout(bookingDetailsPanelLayout);
        bookingDetailsPanelLayout.setHorizontalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanelLayout.createSequentialGroup()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(waitingListLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yesWaitingListRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(noWaitingListRadioButton))
                    .addComponent(entrySlotsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(entrySlotsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(entrySlotBookingListLabel1)
                            .addComponent(entrySlotBookingListLabel2)
                            .addComponent(entrySlotBookingListLabel3)))
                    .addComponent(waitingListFileLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entrySlotBookingListFilePathInput1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(entrySlotBookingListFilePathInput2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(entrySlotBookingListFilePathInput3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(waitingListFilePathInput, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bookingListBrowseButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(waitingListBrowseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(bookingListBrowseButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookingListBrowseButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        bookingDetailsPanelLayout.setVerticalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotsLabel)
                    .addComponent(entrySlotsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entrySlotBookingListLabel1)
                    .addComponent(entrySlotBookingListFilePathInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingListBrowseButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(waitingListLabel)
                            .addComponent(yesWaitingListRadioButton)
                            .addComponent(noWaitingListRadioButton)
                            .addComponent(waitingListFilePathInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(waitingListFileLabel)
                            .addComponent(waitingListBrowseButton)))
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(entrySlotBookingListLabel2)
                            .addComponent(entrySlotBookingListFilePathInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookingListBrowseButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(entrySlotBookingListLabel3)
                            .addComponent(entrySlotBookingListFilePathInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookingListBrowseButton3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        configBackButton.setText(resourceMap.getString("configBackButton.text")); // NOI18N
        configBackButton.setName("configBackButton"); // NOI18N
        configBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addComponent(requireBookingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yesBookingRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(noBookingRadioButton))
                    .addComponent(titleLabel)
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addComponent(eventTitleInputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eventTitleInput))
                    .addGroup(configPanelLayout.createSequentialGroup()
                        .addComponent(configBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventTitleInputLabel)
                    .addComponent(eventTitleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requireBookingLabel)
                    .addComponent(yesBookingRadioButton)
                    .addComponent(noBookingRadioButton))
                .addGap(18, 18, 18)
                .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(configBackButton)
                    .addComponent(okConfigButton))
                .addContainerGap())
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
                    .addComponent(loginOfflineSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
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
        versionLabel.setText(resourceMap.getString("versionLabel.text")); // NOI18N
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
                .addGap(15, 15, 15))
        );

        onlineConfigPanel.setMinimumSize(new java.awt.Dimension(720, 350));
        onlineConfigPanel.setName("onlineConfigPanel"); // NOI18N

        okConfigButton1.setText(resourceMap.getString("okConfigButton1.text")); // NOI18N
        okConfigButton1.setName("okConfigButton1"); // NOI18N
        okConfigButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okConfigButton1ActionPerformed(evt);
            }
        });

        bookingDetailsPanel1.setEnabled(false);
        bookingDetailsPanel1.setName("bookingDetailsPanel1"); // NOI18N

        entrySlotIdLabel1.setText(resourceMap.getString("entrySlotIdLabel1.text")); // NOI18N
        entrySlotIdLabel1.setName("entrySlotIdLabel1"); // NOI18N

        entrySlotsLabel1.setText(resourceMap.getString("entrySlotsLabel1.text")); // NOI18N
        entrySlotsLabel1.setName("entrySlotsLabel1"); // NOI18N

        entrySlotsSpinner1.setName("entrySlotsSpinner1"); // NOI18N
        entrySlotsSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                entrySlotsSpinner1StateChanged(evt);
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

        entrySlotIdLabel3.setText(resourceMap.getString("entrySlotIdLabel3.text")); // NOI18N
        entrySlotIdLabel3.setName("entrySlotIdLabel3"); // NOI18N

        entrySlotIdLabel2.setText(resourceMap.getString("entrySlotIdLabel2.text")); // NOI18N
        entrySlotIdLabel2.setName("entrySlotIdLabel2"); // NOI18N

        waitingListButtonGroup.add(noLoadWaitingListRadioButton);
        noLoadWaitingListRadioButton.setSelected(true);
        noLoadWaitingListRadioButton.setText(resourceMap.getString("noLoadWaitingListRadioButton.text")); // NOI18N
        noLoadWaitingListRadioButton.setName("noLoadWaitingListRadioButton"); // NOI18N

        loadWaitingListLabel.setText(resourceMap.getString("loadWaitingListLabel.text")); // NOI18N
        loadWaitingListLabel.setName("loadWaitingListLabel"); // NOI18N

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

        waitingListButtonGroup.add(yesLoadWaitingListRadioButton);
        yesLoadWaitingListRadioButton.setText(resourceMap.getString("yesLoadWaitingListRadioButton.text")); // NOI18N
        yesLoadWaitingListRadioButton.setName("yesLoadWaitingListRadioButton"); // NOI18N

        generatedTitle1.setText(resourceMap.getString("generatedTitle1.text")); // NOI18N
        generatedTitle1.setEnabled(false);
        generatedTitle1.setName("generatedTitle1"); // NOI18N

        generatedTitle2.setText(resourceMap.getString("generatedTitle2.text")); // NOI18N
        generatedTitle2.setEnabled(false);
        generatedTitle2.setName("generatedTitle2"); // NOI18N

        generatedTitle3.setText(resourceMap.getString("generatedTitle3.text")); // NOI18N
        generatedTitle3.setEnabled(false);
        generatedTitle3.setName("generatedTitle3"); // NOI18N

        loadEventButton1.setText(resourceMap.getString("loadEventButton1.text")); // NOI18N
        loadEventButton1.setName("loadEventButton1"); // NOI18N
        loadEventButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
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

        loadEventButton3.setText(resourceMap.getString("loadEventButton3.text")); // NOI18N
        loadEventButton3.setEnabled(false);
        loadEventButton3.setName("loadEventButton3"); // NOI18N
        loadEventButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadEventButtonActionPerformed(evt);
            }
        });

        generatedTitleLabel1.setText(resourceMap.getString("generatedTitleLabel1.text")); // NOI18N
        generatedTitleLabel1.setName("generatedTitleLabel1"); // NOI18N

        generatedTitleLabel2.setText(resourceMap.getString("generatedTitleLabel2.text")); // NOI18N
        generatedTitleLabel2.setName("generatedTitleLabel2"); // NOI18N

        generatedTitleLabel3.setText(resourceMap.getString("generatedTitleLabel3.text")); // NOI18N
        generatedTitleLabel3.setName("generatedTitleLabel3"); // NOI18N

        aboutEventLabel.setFont(resourceMap.getFont("aboutEventLabel.font")); // NOI18N
        aboutEventLabel.setText(resourceMap.getString("aboutEventLabel.text")); // NOI18N
        aboutEventLabel.setName("aboutEventLabel"); // NOI18N

        searchEventsButton1.setText(resourceMap.getString("searchEventsButton1.text")); // NOI18N
        searchEventsButton1.setName("searchEventsButton1"); // NOI18N
        searchEventsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
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

        searchEventsButton3.setText(resourceMap.getString("searchEventsButton3.text")); // NOI18N
        searchEventsButton3.setEnabled(false);
        searchEventsButton3.setName("searchEventsButton3"); // NOI18N
        searchEventsButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookingDetailsPanel1Layout = new javax.swing.GroupLayout(bookingDetailsPanel1);
        bookingDetailsPanel1.setLayout(bookingDetailsPanel1Layout);
        bookingDetailsPanel1Layout.setHorizontalGroup(
            bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                        .addComponent(entrySlotsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entrySlotsSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(entrySlotIdLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(entrySlotIdInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(loadWaitingListLabel)
                                .addGap(4, 4, 4)
                                .addComponent(yesLoadWaitingListRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(noLoadWaitingListRadioButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(generatedTitleLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generatedTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanel1Layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedTitleLabel3)
                            .addComponent(entrySlotIdLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generatedTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(entrySlotIdInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton3)
                                .addGap(7, 7, 7)
                                .addComponent(searchEventsButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanel1Layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedTitleLabel2)
                            .addComponent(entrySlotIdLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                                .addComponent(entrySlotIdInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadEventButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchEventsButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                            .addComponent(generatedTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                    .addComponent(aboutEventLabel))
                .addContainerGap())
        );
        bookingDetailsPanel1Layout.setVerticalGroup(
            bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aboutEventLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotsLabel1)
                    .addComponent(entrySlotIdLabel1)
                    .addComponent(entrySlotIdInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadEventButton1)
                    .addComponent(searchEventsButton1)
                    .addComponent(entrySlotsSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel1))
                .addGap(33, 33, 33)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdLabel2)
                    .addComponent(entrySlotIdInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadEventButton2)
                    .addComponent(searchEventsButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel2))
                .addGap(37, 37, 37)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrySlotIdInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entrySlotIdLabel3)
                    .addComponent(loadEventButton3)
                    .addComponent(searchEventsButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatedTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatedTitleLabel3))
                .addGap(18, 18, 18)
                .addGroup(bookingDetailsPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadWaitingListLabel)
                    .addComponent(yesLoadWaitingListRadioButton)
                    .addComponent(noLoadWaitingListRadioButton))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        onlineConfigBackButton.setText(resourceMap.getString("onlineConfigBackButton.text")); // NOI18N
        onlineConfigBackButton.setName("onlineConfigBackButton"); // NOI18N
        onlineConfigBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineConfigBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout onlineConfigPanelLayout = new javax.swing.GroupLayout(onlineConfigPanel);
        onlineConfigPanel.setLayout(onlineConfigPanelLayout);
        onlineConfigPanelLayout.setHorizontalGroup(
            onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                        .addComponent(onlineConfigBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 500, Short.MAX_VALUE)
                        .addComponent(okConfigButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(onlineConfigPanelLayout.createSequentialGroup()
                        .addComponent(bookingDetailsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        onlineConfigPanelLayout.setVerticalGroup(
            onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, onlineConfigPanelLayout.createSequentialGroup()
                .addComponent(bookingDetailsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(onlineConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineConfigBackButton)
                    .addComponent(okConfigButton1))
                .addContainerGap())
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

        finishButton.setAction(actionMap.get("finishAction")); // NOI18N
        finishButton.setText(resourceMap.getString("finishButton.text")); // NOI18N
        finishButton.setFocusable(false);
        finishButton.setName("finishButton"); // NOI18N
        finishButton.setRequestFocusEnabled(false);

        searchInputLabel.setText(resourceMap.getString("searchInputLabel.text")); // NOI18N
        searchInputLabel.setFocusable(false);
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

        generatedStudentName.setEditable(false);
        generatedStudentName.setText(resourceMap.getString("generatedStudentName.text")); // NOI18N
        generatedStudentName.setFocusable(false);
        generatedStudentName.setName("generatedStudentName"); // NOI18N

        generatedStudentLabel.setText(resourceMap.getString("generatedStudentLabel.text")); // NOI18N
        generatedStudentLabel.setFocusable(false);
        generatedStudentLabel.setName("generatedStudentLabel"); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(thisMachineLabel)
                    .addComponent(attendeeCountLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localAttendeeCountTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(attendeeCountLabel2)
                    .addComponent(totalLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalAttendeeCountDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(localAttendeeCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(entrySlotLabel1)
                    .addGroup(bookingStatusPanelLayout.createSequentialGroup()
                        .addComponent(attendeeCountLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thisMachineLabel))
                    .addGroup(bookingStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totalAttendeeCountDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalLabel))
                    .addComponent(attendeeCountLabel2))
                .addContainerGap(6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainOnlinePanelLayout = new javax.swing.GroupLayout(mainOnlinePanel);
        mainOnlinePanel.setLayout(mainOnlinePanelLayout);
        mainOnlinePanelLayout.setHorizontalGroup(
            mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainOnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bookingStatusScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addComponent(bookingStatusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainOnlinePanelLayout.createSequentialGroup()
                        .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatedStudentLabel)
                            .addComponent(searchInputLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainOnlinePanelLayout.createSequentialGroup()
                                .addComponent(searchInput, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(generatedStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkingModeToggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(onlineModeToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainOnlinePanelLayout.createSequentialGroup()
                        .addComponent(backButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 519, Short.MAX_VALUE)
                        .addComponent(finishButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainOnlinePanelLayout.setVerticalGroup(
            mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainOnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainOnlinePanelLayout.createSequentialGroup()
                        .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchInputLabel)
                            .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(generatedStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .addComponent(generatedStudentLabel)))
                    .addComponent(onlineModeToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkingModeToggle1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookingStatusScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainOnlinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton1)
                    .addComponent(finishButton))
                .addContainerGap())
        );

        searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
            java.awt.event.InputEvent.CTRL_DOWN_MASK), "save");
    searchInput.getActionMap().put("save", save);

    searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T,
        java.awt.event.InputEvent.CTRL_DOWN_MASK), "toggleBooking");
searchInput.getActionMap().put("toggleBooking", toggleBooking);

searchInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
    "enterStudentNumber");
    searchInput.getActionMap().put("enterStudentNumber",checkBooking);

    setComponent(preConfigPanel);
    setMenuBar(menuBar);
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
                           input == waitingListFilePathInput);
    boolean isIdInput = (input == entrySlotIdInput1 ||
                         input == entrySlotIdInput2 ||
                         input == entrySlotIdInput3);
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
                           input == waitingListFilePathInput);
    boolean isIdInput = (input == entrySlotIdInput1 ||
                         input == entrySlotIdInput2 ||
                         input == entrySlotIdInput3);
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
        if (evt.getSource() == bookingListBrowseButton1) {
            entrySlotBookingListFilePathInput1.setText(file.getPath());
        }
        if (evt.getSource() == bookingListBrowseButton2) {
            entrySlotBookingListFilePathInput2.setText(file.getPath());
        }
        if (evt.getSource() == bookingListBrowseButton3) {
            entrySlotBookingListFilePathInput3.setText(file.getPath());
        }
        if (evt.getSource() == waitingListBrowseButton) {
            waitingListFilePathInput.setText(file.getPath());
        }
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
    if (evt.getSource() == loadEventButton1) {
        id = entrySlotIdInput1.getText();
        titleInput = generatedTitle1;
    }
    else if(evt.getSource() == loadEventButton2) {
        id = entrySlotIdInput2.getText();
        titleInput = generatedTitle2;
    }
    else {
        id = entrySlotIdInput3.getText();
        titleInput = generatedTitle3;
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
        if (evt.getSource() == searchEventsButton1) {
            entrySlotIdInput1.setText(events.get(i).getId());
            generatedTitle1.setText(events.get(i).getTitle());
        }
        if (evt.getSource() == searchEventsButton2) {
            entrySlotIdInput2.setText(events.get(i).getId());
            generatedTitle2.setText(events.get(i).getTitle());
        }
        if (evt.getSource() == searchEventsButton3) {
            entrySlotIdInput3.setText(events.get(i).getId());
            generatedTitle3.setText(events.get(i).getTitle());
        }
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
        if (evt.getSource() == entrySlotIdInput1) {
            loadEventButton1.doClick();
        }
        if (evt.getSource() == entrySlotIdInput2) {
            loadEventButton2.doClick();
        }
        if (evt.getSource() == entrySlotIdInput3) {
            loadEventButton3.doClick();
        }
    }
}//GEN-LAST:event_idInputKeyPressed
    
    private void checkConfiguration() {
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
        int slots = (Integer) entrySlotsSpinner1.getValue();
        boolean useWaitingList = yesLoadWaitingListRadioButton.isSelected();
        app.setWaitingListFlag(false);
        app.setSlots(slots);
        boolean configOK = false;
        String displayTitle = "";

        javax.swing.JTextField[] idArray = {entrySlotIdInput1, entrySlotIdInput2, entrySlotIdInput3};
        List<javax.swing.JTextField> idList = Arrays.asList(idArray);

        javax.swing.JTextField[] titleArray = {generatedTitle1, generatedTitle2, generatedTitle3};
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
            onlineModeToggle.setEnabled(true);
            totalAttendeeCountDisplay.setEnabled(true);
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
            message += " has already been recorded";
            bookingStatus = "Already recorded";
            Utils.successNoise();
        }
        else if(booking.isOnWaitingList()) {
            Utils.pressAlt();
            int reply = JOptionPane.showConfirmDialog(app.getMainFrame(),
                                                      "Student is on the waiting list. "
                                                      + "Allow student to enter?",
                                                      "Student on waiting list",
                                                      JOptionPane.YES_NO_OPTION);
            Utils.releaseAlt();
            Utils.pressAlt();
            Utils.releaseAlt();
            Utils.failureNoise();
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
                showGenericErrorMessage();
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
        waitingListBrowseButton.setEnabled(enabled &&
                                           yesWaitingListRadioButton.isSelected());
        entrySlotBookingListFilePathInput1.setEnabled(enabled);
        entrySlotBookingListFilePathInput2.setEnabled(enabled && slots > 1);
        entrySlotBookingListFilePathInput3.setEnabled(enabled && slots > 2);
        waitingListFilePathInput.setEnabled(enabled &&
                                            yesWaitingListRadioButton.isSelected());
        entrySlotBookingListLabel1.setEnabled(enabled);
        entrySlotBookingListLabel2.setEnabled(enabled && slots > 1);
        entrySlotBookingListLabel3.setEnabled(enabled && slots > 2);
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

        loadEventButton2.setEnabled(enabled && slots > 1);
        entrySlotIdInput2.setEnabled(enabled && slots > 1);
        searchEventsButton2.setEnabled(enabled && slots > 1);

        loadEventButton3.setEnabled(enabled && slots > 2);
        entrySlotIdInput3.setEnabled(enabled && slots > 2);
        searchEventsButton3.setEnabled(enabled && slots > 2);

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
    private javax.swing.JPanel bookingStatusPanel;
    private javax.swing.JScrollPane bookingStatusScrollPane1;
    private javax.swing.JTextArea bookingStatusTextArea1;
    private javax.swing.JToggleButton checkingModeToggle1;
    private javax.swing.JButton configBackButton;
    private javax.swing.JPanel configPanel;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput1;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput2;
    private javax.swing.JFormattedTextField entrySlotBookingListFilePathInput3;
    private javax.swing.JLabel entrySlotBookingListLabel1;
    private javax.swing.JLabel entrySlotBookingListLabel2;
    private javax.swing.JLabel entrySlotBookingListLabel3;
    private javax.swing.JFormattedTextField entrySlotDisplayTextField1;
    private javax.swing.JFormattedTextField entrySlotIdInput1;
    private javax.swing.JFormattedTextField entrySlotIdInput2;
    private javax.swing.JFormattedTextField entrySlotIdInput3;
    private javax.swing.JLabel entrySlotIdLabel1;
    private javax.swing.JLabel entrySlotIdLabel2;
    private javax.swing.JLabel entrySlotIdLabel3;
    private javax.swing.JLabel entrySlotLabel1;
    private javax.swing.JLabel entrySlotsLabel;
    private javax.swing.JLabel entrySlotsLabel1;
    private javax.swing.JSpinner entrySlotsSpinner;
    private javax.swing.JSpinner entrySlotsSpinner1;
    private javax.swing.JFormattedTextField eventTitleInput;
    private javax.swing.JLabel eventTitleInputLabel;
    private javax.swing.JButton finishButton;
    private javax.swing.JLabel generatedStudentLabel;
    private javax.swing.JTextField generatedStudentName;
    private javax.swing.JTextField generatedTitle1;
    private javax.swing.JTextField generatedTitle2;
    private javax.swing.JTextField generatedTitle3;
    private javax.swing.JLabel generatedTitleLabel1;
    private javax.swing.JLabel generatedTitleLabel2;
    private javax.swing.JLabel generatedTitleLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadEventButton1;
    private javax.swing.JButton loadEventButton2;
    private javax.swing.JButton loadEventButton3;
    private javax.swing.JLabel loadWaitingListLabel;
    private javax.swing.JFormattedTextField localAttendeeCountTextField;
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JSeparator loginOfflineSeparator;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel mainOnlinePanel;
    private javax.swing.JLabel mainTitle;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton noBookingRadioButton;
    private javax.swing.JRadioButton noLoadWaitingListRadioButton;
    private javax.swing.JRadioButton noWaitingListRadioButton;
    private javax.swing.JButton okConfigButton;
    private javax.swing.JButton okConfigButton1;
    private javax.swing.JButton onlineConfigBackButton;
    private javax.swing.JPanel onlineConfigPanel;
    private javax.swing.JToggleButton onlineModeToggle;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel preConfigPanel;
    private javax.swing.ButtonGroup requireBookingButtonGroup;
    private javax.swing.JLabel requireBookingLabel;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton searchEventsButton1;
    private javax.swing.JButton searchEventsButton2;
    private javax.swing.JButton searchEventsButton3;
    private javax.swing.JFormattedTextField searchInput;
    private javax.swing.JLabel searchInputLabel;
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
    private javax.swing.JFormattedTextField waitingListFilePathInput;
    private javax.swing.JLabel waitingListLabel;
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
