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
    // Write out some opening HTML, CSS, and Javascript
    echo "<html>";
    echo "<script language=\"javascript\">
    function hide(className) {
        var divsToHide = document.getElementsByClassName(className);

        for(var i = 0; i < divsToHide.length; i++)
        {
            divsToHide[i].style.display='none';
        }
        return false;
    }
    function show(className) {
        var divsToHide = document.getElementsByClassName(className);

        for(var i = 0; i < divsToHide.length; i++)
        {
            divsToHide[i].style.display='list-item';
        }
        return false;
    }
    </script>";
    echo "<style>
    .moreResults {
        display: none;
    }
    a {
        color: red;
        cursor: pointer;
    }
    </style>";
    echo "<body><ul>";
    
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
            
            // print out just the name in HTML
            echo "<li>" . $name_only . ", ".$data[12]. " (" . $data[0].") : \n<br>" .
                "&nbsp;&nbsp;searched: $name\n<ul>";
            
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
                    // Round the strength to a manageable precision
                    $result["strength"] = round($result["strength"], 2);

                    // The first one gets no class in HTML, so it is shown by default
                    if ($i == 0) {
                        echo "   <li>" . $result["identity"]["nameEntries"][0]["original"] . 
                            " (<a href=\"" . $result["identity"]["ark"] ."\">". $result["identity"]["ark"] . "</a>) ".
                            "-- score: " . $result["strength"] . "</li>\n";
                    // The next 4 results have a class so we can show/hide them in the HTML output
                    } else if ($i < 5)  {// Maximum of 5 results shown in the output
                        echo "   <li class='moreResults res$row'>" . $result["identity"]["nameEntries"][0]["original"] . 
                            " (<a href=\"" . $result["identity"]["ark"] ."\">". $result["identity"]["ark"] . "</a>) ".
                            "-- score: " . $result["strength"] . "</li>\n";
                    }
                }
            }
            // Print out show/hide options for this reconciliation result
            echo "<li><a href='#' onClick=\"show('res$row')\">+</a> / <a href='#' onClick=\"hide('res$row')\">-</a></li></ul></li>";
        }	
    }
    fclose($handle);
    
    // Close the result list and HTML
    echo "</ul></body></html>";
} else {
    // If something happened, then just print out usage
    echo "CBW Reconciliation Script (generate HTML)\n" .
        "Usage: php cbwhtml.php datafile.csv > reconciliation_output.html\n\n" .
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
    
