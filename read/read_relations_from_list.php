<?php
/**
 * SNAC Export from List example 
 *
 * For the full license, see the LICENSE file in the repository root
 *
 * @author Robbie Hott
 * @license http://opensource.org/licenses/BSD-3-Clause BSD 3-Clause
 * @copyright 2017 the Rector and Visitors of the University of Virginia, and
 *            the Regents of the University of California
 */

$row = 0;

// Open the first argument as the csv file to read (argv[1] should be the CBW csv file)
if (isset($argv[1]) && (($handle = fopen($argv[1], "r")) !== FALSE)) {

    // Loop over the data in the CSV file
    while (($data = fgetcsv($handle)) !== FALSE) {
        if ($row++ >= 1) { // ignore the header row

            // Build a SNAC query with an Identity Constellation Array (JSON object)
            $snacArray = array(
                "command" => "read_constellation",
                "constellationid" => $data[0]
            );
            
            // Encode Associative Array as JSON object
            $query = json_encode($snacArray, JSON_PRETTY_PRINT);

            // Use CURL to send reconciliation request to the REST API
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, "http://api.snaccooperative.org/");
            curl_setopt($ch, CURLOPT_HTTPHEADER,
                array (
                    'Content-Type: application/json',
                    'Content-Length: ' . strlen($query)
                ));
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'PUT');
            curl_setopt($ch, CURLOPT_POSTFIELDS, $query);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $responseJSON = curl_exec($ch);
            curl_close($ch);

            // Decode JSON response into an Associative Array
            $response = json_decode($responseJSON, true);
        }	
    }
    fclose($handle);

} else {
    // If something happened, then just print out usage
    echo "Download from list of ICIDs\n" .
        "Usage: php read_from_list.php list.csv [directory] [type]\n\n" .
        "The list.csv file should have a header and the first column should contain ICIDs.\n" .
        "Type must be a valid type: eac-cpf, constellation_json\n";
}



?>

