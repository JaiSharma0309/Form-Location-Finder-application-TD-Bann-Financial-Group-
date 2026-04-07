# Form Location Finder for Banking Request PDFs

This project is a small automation tool built to solve a real workflow problem: locating the correct request PDF inside a large directory of poorly named system-generated folders.

In the original business process, IT staff would receive a form ID from an employee and then manually search through hundreds of generated folders to find the corresponding `output.pdf`. Because the folder names were not human-readable, the search process was slow, repetitive, and easy to get wrong.

The key insight was that each folder also contained a metadata file called `FORMS_CTRLFUNC_RPT.DAT`. That file stored the form ID needed to identify the correct folder, making it possible to automate the lookup instead of opening folders one by one.

All files in this repository are dummy samples for demonstration purposes.

## What The Project Does

The project uses a shell script and a Java program together:

1. A shell script accepts a root directory and a form ID.
2. It loops through each subfolder inside the root directory.
3. For each subfolder, it runs the Java program.
4. The Java program reads `FORMS_CTRLFUNC_RPT.DAT`, splits each line on the `|` delimiter, and checks whether the requested form ID exists in that file.
5. If a match is found, the program prints the folder path so the matching `output.pdf` can be retrieved from that same directory.

## Folder Structure

```text
.
├── README.md
├── data
│   ├── FORMS_CTRLFUNC_RPT.DAT
│   └── output.pdf
├── scripts
│   └── find-form-folder.sh
└── src
    └── FormNumberExtractor.java
```

## How To Run

### Requirements

- Java JDK 17 or later
- Bash or a Unix-like shell

### Compile And Run With The Script

From the repository root:

```bash
bash scripts/find-form-folder.sh <root-directory> <form-id>
```

Example using the sample data in this repository:

```bash
mkdir -p sample-root/request-a
cp data/FORMS_CTRLFUNC_RPT.DAT sample-root/request-a/
cp data/output.pdf sample-root/request-a/

bash scripts/find-form-folder.sh sample-root 525812
```

Expected output:

```text
Root directory: sample-root
Form ID number: 525812
The folder in which the file resides is: sample-root/request-a
```

## Running The Java Program Directly

If you want to compile and run the Java code yourself:

```bash
javac src/FormNumberExtractor.java
java -cp src FormNumberExtractor <folder-path> <form-id>
```

Example:

```bash
java -cp src FormNumberExtractor data 525812
```

This direct Java command checks a single folder. The shell script is what performs the full directory scan across many subfolders.

## Why This Is Useful

- Removes a manual folder-by-folder search process
- Speeds up retrieval of customer request PDFs
- Reduces the chance of human error
- Demonstrates practical scripting and file parsing for a real operational problem

## Technical Notes

- The Java logic uses `BufferedReader` to read the metadata file line by line.
- It uses `StringTokenizer` with `|` as the delimiter to inspect each field.
- The script compiles the Java file and scans every subdirectory under the provided root path.
- The program returns the folder location rather than the PDF contents because the PDF is expected to be stored alongside the metadata file.

## Future Improvements

- Return immediately after the first match instead of continuing to scan
- Improve error handling for missing files and invalid directories
- Add clearer success and no-match messages
- Convert the script into a single packaged CLI tool
- Add unit tests for parsing logic
- Support recursive searches for deeper directory structures
- Output the full PDF path directly instead of just the folder path
- Replace `StringTokenizer` with a more modern parsing approach

## Project Summary

This repository is a lightweight example of using automation to improve an inefficient legacy workflow. It highlights practical problem-solving, Java file parsing, and shell-based directory traversal in a business context.
