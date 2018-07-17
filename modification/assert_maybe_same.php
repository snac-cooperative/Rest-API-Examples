<?php
/**
 * SNAC Assert Maybe-same example
 *
 * Contains a function that, given two ARK ids, asks SNAC to denote them
 * as maybe the same. 
 *
 * For the full license, see the LICENSE file in the repository root
 *
 * @author Robbie Hott
 * @license http://opensource.org/licenses/BSD-3-Clause BSD 3-Clause
 * @copyright 2017 the Rector and Visitors of the University of Virginia
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


/**
 * Ask SNAC to assert Maybe Same
 *
 * Asks SNAC to assert that the two constellations given by $ark1 and $ark2
 * may-be the same identity.  Since this requires permissions, a user object
 * must be supplied for the operation to complete.  However, either the JSON string
 * or a PHP associative array are allowed.
 *
 * @param string $url The SNAC API URL to use
 * @param string $ark1 The first Constellation's ARK or SNAC ID
 * @param string $ark2 The second Constellation's ARK or SNAC ID
 * @param string[]|string $user The user object (JSON string or PHP Array)
 * @param string $message optional A message to include when asserting maybe-same
 * @return boolean True if successful, false otherwise
 */
function assertMaybeSame($url, $ark1, $ark2, $user=null, $message=null) {
    
    // Preliminary check on the user, which MUST be supplied
    if ($user == null) {
        return false;
    }

    // Preliminary checks on the arks
    if ($ark1 == $ark2) 
        return false;
    if ($ark1 == "" || $ark2 == "" || $ark1 == null || $ark2 == null)
        return false;

    $realUser = $user;
    // Convert user to JSON
    if (!is_array($user)) {
        $realUser = json_decode($user, true);
        if ($realUser == null)
            return false;
    }

    // Read the first IC from snac
    $snacArray = array(
        "command" => "read",
        is_numeric($ark1) ? "constellationid" : "arkid" => $ark1,
        "type" => "summary" 
    );
    $response = json_decode(querySNAC(json_encode($snacArray), $url), true);
    if (!isset($response["constellation"])) {
        return false;
    }
    $ic1 = $response["constellation"];

    // Read the first IC from snac
    $snacArray = array(
        "command" => "read",
        is_numeric($ark2) ? "constellationid" : "arkid" => $ark2,
        "type" => "summary" 
    );
    $response = json_decode(querySNAC(json_encode($snacArray), $url), true);
    if (!isset($response["constellation"])) {
        return false;
    }
    $ic2 = $response["constellation"];


    echo "Adding maybe same\n";
    // Ask SNAC to make the connection
    $maybeSameCommand = array(
        "command" => "constellation_add_maybesame",
        "constellationids" => [
            $ic1["id"],
            $ic2["id"]
        ],
        "user" => $realUser
    );
    if ($message != null && $message != "") {
        $maybeSameCommand["assertion"] = $message;
    }
    $response = json_decode(querySNAC(json_encode($maybeSameCommand), $url), true);
    if (isset($response["result"]) && $response["result"] == "success")
        return true;
    else
        print_r($response);

    return false;
}


$user = ''; // From the API Key information for your user

// Example usage, for snac-dev
if (assertMaybeSame("http://snac-dev.iath.virginia.edu/api/", 'http://n2t.net/ark:/99166/w6096v84', 'http://n2t.net/ark:/99166/w6vt3b3g', $user, "My assertion reasoning."))
    echo "Success!\n";
else
    echo "Failure\n";
