<?php
/**
 * SNAC Insert and Publish Example 
 *
 * For the full license, see the LICENSE file in the repository root
 *
 * @author Robbie Hott
 * @license http://opensource.org/licenses/BSD-3-Clause BSD 3-Clause
 * @copyright 2017 the Rector and Visitors of the University of Virginia, and
 *            the Regents of the University of California
 */

// Helper function to query SNAC
function querySNAC($query, $url) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_HTTPHEADER,
        array (
            'Content-Type: application/json',
            'Content-Length: ' . strlen($query)
        ));
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'PUT');
    curl_setopt($ch, CURLOPT_POSTFIELDS, $query);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    curl_close($ch);
    return $response;
}


// SNAC Development URL.  Replace with correct URL
$SNAC_URL = "http://snac-dev.iath.virginia.edu/api";

$USER = ''; // From snac's API key page (everything in the user object, i.e. '{...}' only

$name = "Smith, Martha (2017-2018)";

// Create a Constellation as an associative array with all the information needed
// operation needs to be insert on every compnent so that SNAC will insert them
$constellation = array (
    "dataType" => "Constellation",
    "operation" => "insert",
    "entityType" => array (
        "dataType" => "Term",
        "id" => 700,
        "term" => "person"),
    "nameEntries" => array ( 
        array ( 
            "dataType" => "NameEntry",
            "original" => $name,
            "preferenceScore" => 99,
            "operation" => "insert",
            "components" => array (
                array (
                    "dataType" => "NameComponent",
                    "text" => $name,
                    "operation" => "insert",
                    "order" => 0,
                    "type" => array ("id" => 400228)
                )
            )
        )
    )
);


// Create the API Query to SNAC to insert the constellation
$snacArray = array(
    "command" => "insert_constellation",
    "user" => json_decode($USER, true),
    "constellation" => $constellation,
    "message" => "Fancy script write.",
);

// Curl to Query SNAC 
$query = json_encode($snacArray, JSON_PRETTY_PRINT);
$response = querySNAC($query, $SNAC_URL);

// Get the associative array of the response (more useful in PHP)
$inserted = json_decode($response, true);

// If SNAC didn't return a written constellation, then something happened
if (!isset($inserted["constellation"])) {
    print_r($inserted);
    die ("Error occurred ". $response);
}

// Print it out just to see:
echo "Inserted Constellation: " . print_r($inserted["constellation"], true) . "\n";

// We can now get the new ID given to the constellations
$newid = $inserted["constellation"]["id"];

// Update the original query to now do a publish for us (so that the Constellation is public)
$snacArray["constellation"] = $inserted["constellation"];
$snacArray["command"] = "publish_constellation";

// Curl to Query SNAC to publish the constellation
$query = json_encode($snacArray, JSON_PRETTY_PRINT);
$response = querySNAC($query, $SNAC_URL);

// Get the associative array of the response (more useful in PHP)
$published = json_decode($response, true);

// Check to see if the Constellation was published
// SNAC should have returned a published constellation
if (!isset($published["constellation"])) {
    echo "Queried: " . print_r($snacArray, true);
    die ("Error occurred ". $response);
}

// Print out the new Constellation information
echo "Created \"$name\" as Constellation in SNAC\n\t\tConstellationID: $newid\n\t\tArkID: {$published["constellation"]["ark"]}\n";
