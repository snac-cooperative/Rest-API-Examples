# CBW Reconciliation Example

## Overview

This example provides a simple identity reconciliation query to the SNAC REST API.  In this example, each command-line php script takes one argument: a CSV file.  The CSV file has information about an identity: first name, middle name, last name, and life dates.  The scripts read the CSV file, create a JSON Constellation object (via an associative array), use CURL to query SNAC, then format and print the reconciliation results.

## Usage

To run the scripts, you need a compatible CSV file, with the following columns:

```
"Unique ID",Prefix,First,Middle,Last,Suffix,(ignored),(ignored),(ignored),(ignored),(ignored),(ignored),"Life Dates",(ignored)
```

With that CSV, the scripts may be run as follows:

```
php cbwcsv.php datafile.csv > reconciliation_output.csv
php cbwhtml.php datafile.csv > reconciliation_output.html
```
