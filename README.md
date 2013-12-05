# EventSwipe

*Current version: Avocet*

EventSwipe is a simple Java application for recording attendance at events.

It works by reading some kind of ID string (a student number, for example) and either checking this against a booking list or recording the ID in a text file to be processed later. The ID can be entered from a keyboard or any other external input device, such as an RFID reader.

---

##Installation

###Requirements

* Any device capable of running a Java application.
* A way of distinguishing event attendees with unique and easily accessible IDs. For example, student numbers which can be read from student cards via a magnetic strip or RFID chip.

---

##Usage

###Preparing the booking lists

If the event didn't require students to book in advance then ignore this section.

EventSwipe can read ID strings from an ANSI encoded .txt file separated by line breaks. Make sure your booking lists are saved in this format and accessible from the device running EventSwipe. 

You can upload up to three booking list files (for different timeslots within the same event) and a waiting list.

###Running the application

When you start up EventSwipe, you'll be asked to enter some details about the event. You need to enter a title and indicate whether or not you want to use a booking list and a waiting list. You can then upload all the necessary lists of IDs.

Hit ok to start recording attendance. Depending on the event settings and the attendee's booking status, when you enter an ID EventSwipe will display the following messages:

####'Success' messages

* __Booked__ - ID is on the booking list and entry has been recorded. EventSwipe will also display what entry slot the attendee has been booked for (if applicable)
* __Recorded__ - ID has been recorded (displayed when a booking list is not used) 

####Other messages

* __Already recorded__ - ID has already been entered and recorded
* __Not booked__ - ID is not on the booking list and is not recorded in the attendees list
* __Waiting list__ - ID is on the waiting list. EventSwipe will also open a dialogue box asking you whether or not to let the attendee into the event. Selecting 'Yes' records the student number and takes the student off the waiting list, 'No' keeps the student on the waiting list.

To record an ID which was not on the booking list, you need to switch EventSwipe to 'record all mode' with the button in the top right. You can then enter a non-booked ID and record it. This mode is useful if you want to let in a batch of non-booked attendees in one go and can be used as a fall back to guarantee you record entry to your event, even if something goes wrong with your booking list.

###Saving the attendance list

When you have finished recording entry to your event, hit 'save', choose a file name and location and EventSwipe will save the list of recorded IDs (along with the entry slot they were booked for, if applicable) as a text file. Each ID is separated by a line break, although it won't look like it when you open the file. You'll see this is the case if you open the list in a programme like Excel.

__Important:__ Do not close EventSwipe before saving your attendees list or you will lose all your attendance data.

---

##Future features

###Buzzard

*Expected release: January 2014*

* Handle multiple text file encodings
* Session logging (in case of application crashing or not saving attendance data)
* Yes/no dialogue for non-booked attendees with keyboard shortcuts
* Stronger visual cue when ID has been entered
* Keyboard shortcut for booking mode toggling
* Max capacity feature (for single slot events)
* Exit confirmation (based on save flag)

###Condor

*Expected release: October 2014*

* Handle multiple booking file types (including .csv)
* Counting mode
* Entry slot dialogue to indicate that attendee is too late/early according to system time
* Duplicate slot booking report feature
