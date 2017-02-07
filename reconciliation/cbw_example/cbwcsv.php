<?php
/**
 * SNAC Reconciliation Example (CBW)
 *
 * For the full license, see the LICENSE file in the repository root
 *
 * @author Robbie Hott
 * @license http://opensource.org/licenses/BSD-3-Clause BSD 3-Clause
 * @copyright 2017 the Rector and Visitors of the University of Virginia, and
 *            the Regents of the University of California
 */

$row = 0;

// Open the first argument as the file to read (argv[1] should be the CBW csv file)
if (isset($argv[1]) && (($handle = fopen($argv[1], "r")) !== FALSE)) {

    // Write out header of the CSV
    $output = array(
        "CBW Name",
        "CBW ID",
        "Snac Name",
        "Snac ARK",
        "Overall Reconciliation Score",
        "Elastic Full Name Score",
        "Elastic Name-Only Score",
        "Elastic75 Score",
        "Original Length Score",
        "Original Length Difference Score",
        "SNAC Degree Score"
    );
    fputcsv(STDOUT, $output);    

    // Loop over the data in the CSV file
    while (($data = fgetcsv($handle)) !== FALSE) {
        if ($row++ >= 1) { // ignore the header row

            // grab the name components from the CSV file and create a snac-like name heading
            $name_only = trim(trim($data[4]) . ", " . trim($data[2]) . " " . trim($data[3]));
            if (substr($name_only, -1) === ",") {
                $name_only = substr($name_only, 0,strlen($name_only) -1);
            }

            // if the input file has a 12th column, dates, then put them into the name
            if (isset($data[12])) {
                $dates = array();
                preg_match_all("/[0-9][0-9][0-9][0-9]/", $data[12], $dates);
                $name = trim($name_only);
                if (count($dates[0]) > 0)
                    $name .= ", " . implode("-", $dates[0]);
            } else {
                $name = $name_only;
            }
            
            // Build a SNAC Identity Constellation Array (JSON object)
            $snacArray = array(
                "command" => "reconcile",
                "constellation" => array (
                    "dataType" => "Constellation",
                    "nameEntries" => array ( 
                        array ( 
                            "dataType" => "NameEntry",
                            "original" => $name,
                            "preferenceScore" => 1
                        )
                    )
                )
            );

            $query = json_encode($snacArray, JSON_PRETTY_PRINT);

            // Use CURL to send reconciliation request to the REST API
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, "http://snac-web.iath.virginia.edu:81/");
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

            $response = json_decode($responseJSON, true);
            if (isset($response["reconciliation"])) {
                foreach ($response["reconciliation"] as $i => $result) {
                    
                    // only grab the first 5 results
                    if ($i > 5) break;
                    
                    // build the CSV line to print
                    $output = array(
                        $name,
                        $data[0],
                        $result["identity"]["nameEntries"][0]["original"],
                        $result["identity"]["ark"],
                        round($result["strength"], 2),
                        isset($result['vector']["ElasticOriginalNameEntry"]) ? $result['vector']["ElasticOriginalNameEntry"] : 0,
                        isset($result['vector']["ElasticNameOnly"]) ? $result['vector']["ElasticNameOnly"] : 0,
                        isset($result['vector']["ElasticSeventyFive"]) ? $result['vector']["ElasticSeventyFive"] : 0,
                        isset($result['vector']["OriginalLength"]) ? $result['vector']["OriginalLength"] : 0,
                        isset($result['vector']["MultiStage:ElasticNameOnly:OriginalLengthDifference"]) ? $result['vector']["MultiStage:ElasticNameOnly:OriginalLengthDifference"] : 0,
                        isset($result['vector']["MultiStage:ElasticNameOnly:SNACDegree"]) ? $result['vector']["MultiStage:ElasticNameOnly:SNACDegree"] : 0
                    );
                    fputcsv(STDOUT, $output);    
                }
            }
        }	
    }
    fclose($handle);

} else {
    // If something happened, then just print out usage
    echo "CBW Reconciliation Script (generate CSV)\n" .
        "Usage: php cbwcsv.php datafile.csv > reconciliation_output.csv\n\n" .
        "The CSV input file should have the following headers (in this order):\n" .
        "    1 : CBW ID\n".
        "    2 : (column two ignored)\n".
        "    3 : First Name\n".
        "    4 : Middle Name\n".
        "    5 : Last Name\n".
        "        ... ignored columns ...\n".
        "    13: Life Dates (optional)\n\n";
}



?>

