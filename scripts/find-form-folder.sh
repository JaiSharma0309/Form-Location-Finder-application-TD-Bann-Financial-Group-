#!/usr/bin/env bash

# Exit on the first error, on undefined variables, and on pipeline failures.
set -euo pipefail

# Validate that the user provided both required arguments.
if [ "$#" -ne 2 ]; then
    echo "Usage: bash scripts/find-form-folder.sh <root-directory> <form-id>"
    exit 1
fi

# Store the user inputs in named variables for clarity.
ROOT_DIRECTORY="$1"
FORM_ID="$2"

# Resolve the repository root so the script can be run from anywhere.
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

# Echo the inputs so the user can confirm the search target.
echo "Root directory: $ROOT_DIRECTORY"
echo "Form ID number: $FORM_ID"

# Compile the Java source before scanning the directories.
javac "$REPO_ROOT/src/FormNumberExtractor.java"

# Check each immediate child folder under the root directory.
for DIRECTORY in "$ROOT_DIRECTORY"/*; do
    if [ -d "$DIRECTORY" ]; then
        # Search this folder's metadata file for the requested form ID.
        java -cp "$REPO_ROOT/src" FormNumberExtractor "$DIRECTORY" "$FORM_ID"
    fi
done
