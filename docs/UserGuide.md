---
layout: page
title: User Guide
---

MediFlowR is a **desktop app for managing patient records and appointments, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, MediFlowR can get your patient management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `mediflowr.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your patient records.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar mediflowr.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all patients.

   * `add name=John birthdate=30/09/2001 gender=Male illness=Fever` : Adds a patient named `John` to the records.

   * `delete id=3` : Deletes the patient with id 3 in the current list.

   * `clear` : Deletes all patients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in between square brackets `[]` are the parameters to be supplied by the user.<br>
  e.g. in `search name=[name]`, `[name]` is a parameter which can be used as `add n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `name=[name] birthdate=[birthdate]`, `birthdate=[birthdate] name=[name]` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a new patient: `add`

Creates a new patient profile to be added to the patient records. 

Format: `add name=[name] birthdate=[birthdate] gender=[gender]
illness=[illness]​`

Examples:
*  `add name=John birthdate=30/09/2001 gender=Male illness=Fever`

* `add name=Cena birthdate=30/12/1993 gender=Male illness=Schizophrenia`

### Listing all patients : `list`

Shows a list of all patients in the address book.

Format: `list`

### Updating Patient Treatment History: `update`

Updates a patient's treatment history with illness.

Format: `update id=[patient-id] illness=[illness]`

* Updates the illness of the patient with id `patient-id`.
* Existing illness will be changed to the `illness`.

Examples:
*  `update id=12345 illness=Fever` updates the illness of the patient with patient ID `12345` to `Fever`.

### Locating patients by name: `search`

Finds patients whose names contain any of the given keywords.

Format: `search name=[name]`

* The search is case-insensitive. e.g `tianrun` will match `Tianrun`
* The order of the keywords does not matter. e.g. `Lebron James` will match `James Lebron`
* Only the name is searched.
* Only full words will be matched e.g. `Curr` will not match `Curry`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Chris Paul` will return `Chris Bumstead`, `Logan Paul`

Examples:
* `search name=James` returns `james` and `James Harden`
* `search name=alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a patient : `delete`

Deletes the specified patient from the patient list.

Format: `delete id=[patient-id]`

* Deletes the patient with the specified `patient-id`.
* The patient id refers to the id of the patient shown in the displayed patient list.
* The patient id **must be a positive integer** 1, 2, 3, …​

Examples:
`delete id=12345` deletes the patient with id **12345** in the patient list.

### Clearing all patient records : `clear`

Clears all patient records from the system.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

MediFlowR data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

MediFlowR data are saved automatically as a JSON file `[JAR file location]/data/mediflowr.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MediFlowR home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add name=[name] birthdate=[birthdate] gender=[gender] illness=[illness]` <br> e.g., `add name=John birthdate=30/09/2001 gender=Male illness=Fever`
**Clear** | `clear`
**Delete** | `delete id=[patient-id]`<br> e.g., `delete id=3`
**Update** | `update id=[patient-id] illness=[illness]`<br> e.g.,`update id=12345 illness=Fever`
**Search** | `search name=[name]`<br> e.g., `search name=James Jake`
**List** | `list`
**Help** | `help`
