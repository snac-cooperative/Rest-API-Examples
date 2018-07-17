# Add Resource and Resource Relation

This walkthrough will guide you through adding a Resource to SNAC, then connecting that Resource to an existing Constellation entity.  You'll first need to grab your user credentials by logging into SNAC and visiting the Rest API Key page available under the User menu.  That page will also provide the API URL.  For demonstration purposes, we'll be using the snac-dev API at `http://snac-dev.iath.virginia.edu/api/`.

## Adding the Resource

We'll first add a Resource.  Let us consider the following resource information:

| Field               | Data                                                                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------|
| Title               | The Complete Sherlock Holmes                                                                           |
| Author              | Arthur Conan Doyle                                                                                     |
| Abstract            | The complete chronicles of the master detective Sherlock Holmes, as told by his associate John Watson. |
| URL                 | http://example.edu/findingAids/holmes12345                                                             |
| Holding Institution | Sherlock Holmes Society of London, http://snac-dev.iath.virginia.edu/view/43069962                     |

We must first convert this into a SNAC-compatible JSON Resource object:
```json
{
    "dataType": "Resource",
    "documentType": {
        "id": "696",
        "term": "ArchivalResource",
        "uri": "http:\/\/socialarchive.iath.virginia.edu\/control\/term#ArchivalResource",
        "type": "document_type"
    },
    "link": "http:\/\/example.edu\/findingAids\/holmes12345",
    "title": "The Complete Sherlock Holmes",
    "displayEntry": "Doyle, Arthur Conan. The Complete Sherlock Holmes.",
    "abstract": "The complete chronicles of the master detective Sherlock Holmes, as told by his associate John Watson.",
    "extent": "2 volumes",
    "repository": {
        "dataType": "Constellation",
        "id": "43069962"
    },
    "operation": "insert"
}
```
The data type *is* case sensitive, which has caused some trouble in the past.  We're working on either removing that or clearing up the case sensitivity issue.  We used the ArchivalResource type for the document and provided the link, title, abstract, and extent.   The display entry is a human-readable and distinguishable field, so we added the author's name to the beginning.  The repository link is a minimalistic Constellation with the ID of the holding institution we found above.  Lastly, we include an "operation" field that will tell the REST API we want to insert this resource (rather than update it).

That Resource object can be inserted into SNAC by using the `insert_resource` command at the REST API endpoint.  We can PUT the following JSON object to `http://snac-dev.iath.virginia.edu/api/` to insert the resource:
```json
{
    "command": "insert_resource",
    "user": { "...": "YOUR API INFO" },
    "resource": {
        "dataType": "Resource",
        "documentType": {
            "id": "696",
            "term": "ArchivalResource",
            "uri": "http:\/\/socialarchive.iath.virginia.edu\/control\/term#ArchivalResource",
            "type": "document_type"
        },
        "link": "http:\/\/example.edu\/findingAids\/holmes12345",
        "title": "The Complete Sherlock Holmes",
        "displayEntry": "Doyle, Arthur Conan. The Complete Sherlock Holmes.",
        "abstract": "The complete chronicles of the master detective Sherlock Holmes, as told by his associate John Watson.",
        "extent": "2 volumes",
        "repository": {
            "dataType": "Constellation",
            "id": "43069962"
        },
        "operation": "insert"
    }
}
```

The REST API will return the full written Resource object with IDs and Version numbers from the SNAC REST API.  Hang on to this object, since we'll use it when we update a Constellation later to connect with this resource.
```json
{
    "resource": {
        "dataType": "Resource",
        "documentType": {
            "id": "696",
            "term": "ArchivalResource",
            "uri": "http://socialarchive.iath.virginia.edu/control/term#ArchivalResource",
            "type": "document_type"
        },
        "link": "http://example.edu/findingAids/holmes12345",
        "title": "The Complete Sherlock Holmes",
        "abstract": "The complete chronicles of the master detective Sherlock Holmes, as told by his associate John Watson.",
        "extent": "2 volumes",
        "displayEntry": "Doyle, Arthur Conan. The Complete Sherlock Holmes.",
        "repository": {
            "dataType": "Constellation",
            "ark": "http://n2t.net/ark:/99166/w6cp2jrc",
            "entityType": {
                "id": "698",
                "term": "corporateBody",
                "uri": "http://socialarchive.iath.virginia.edu/control/term#CorporateBody",
                "type": "entity_type"
            },
            "nameEntries": [
                {
                    "dataType": "NameEntry",
                    "original": "Sherlock Holmes Society of London",
                    "preferenceScore": "99",
                    "id": "43069972",
                    "version": "6291782"
                }
            ],
            "id": "43069962",
            "version": "6291783"
        },
        "id": "11612940",
        "version": "8359842"
    },
    "result": "success",
    "timing": 109.63
}
```
One thing to notice in this response is that the system filled out the repository information, giving us the preferred name entry, entity type, and ark of the holding repository.  If there were place information, including latitude, longitude, and address, that would have been included as well.  At the bottom of our Resource object, we can see it now has an id: `11612940`.  We can now view our resource online at http://snac-dev.iath.virginia.edu/vocab_administrator/resource/11612940.

## Adding the Resource Relation

Now, let's say we want to say that Sherlock Holmes, http://snac-dev.iath.virginia.edu/view/50447072, was referenced in our new Resource.  We will go through the following steps to make that connection:
1. Check out Sherlock Holmes for editing
2. Update Sherlock Holmes to add the resource relation
3. Publish Sherlock Holmes to make the change public

### Checking Out Sherlock Holmes

We first must reserve Sherlock Holmes for editing.  We can do this by issuing the following PUT request to the REST API.
```json
{
    "command": "edit",
    "constellationid": 50447072,
    "user": { "...": "YOUR API INFO" }
}
```
The REST API will respond with the ENTIRE constellation so that you will have a baseline from which to make edits.  Since it is so large, the full response is available in the `sherlock_example.json` file in this directory.  Here is the most relevent subset of the response:
```json
{
    "constellation": {
        "dataType": "Constellation",
        "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
        "entityType": {
            "id": "700",
            "term": "person",
            "uri": "http://socialarchive.iath.virginia.edu/control/term#Person",
            "type": "entity_type"
        },
        "id": 50447072,
        "version": "11254455",
        "dates": [
            {
                "dataType": "SNACDate",
                "fromDate": "1854-01-06",
                "fromDateOriginal": "1854-01-06",
                "fromType": {
                    "id": "689",
                    "term": "Birth",
                    "uri": "http://socialarchive.iath.virginia.edu/control/term#Birth",
                    "type": "date_type"
                },
                "isRange": true,
                "id": "50447073",
                "version": "7374452"
            }
        ]
    },
    "timing": 726.73
}
```
Since we did not get an error, we are now in "possession" of the Constellation to make edits.  We must note down the id and version above, as we will need that to make our updates.

### Update Sherlock Holmes to Add the Resource Relation

Now, let us construct a partial Constellation for Holmes using the response we just got from the REST API and our new Resource object from above:
```json
{
    "dataType": "Constellation",
    "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
    "id": 50447072,
    "version": "11254455",
    "resourceRelations": [
        {
            "dataType": "ResourceRelation",
            "resource": {
                "id": "11612940",
                "version": "8359842"
            },
            "role": {
                "id": "693",
                "term": "referencedIn",
                "uri": "http://socialarchive.iath.virginia.edu/control/term#referencedIn",
                "type": "document_role"
            },
            "operation": "insert"
        }
    ]
}
```
This Constellation only has the minimal amount of information we need to add a resource relation.  We must identify the Constellation, so we must include the id and correct version number.  Also note that the dataType is case sensitive.  We also only need a minimal resource that includes the ID and Version from the Resource object that we created above, since the system has all of the other information stored.  We have asserted by the role value that Sherlock was "referenced in" this resource, and lastly, we've added the "operation" to the Resource Relation so that the system will know to insert it as new.

We can then submit the update call as a PUT to the REST API as follows:
```json
{
    "command": "update_constellation",
    "user": { "...": "YOUR API INFO" },
    "constellation": {
        "dataType": "Constellation",
        "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
        "id": 50447072,
        "version": "11254455",
        "resourceRelations": [
            {
                "dataType": "ResourceRelation",
                "resource": {
                    "dataType": "Resource",
                    "id": "11612940",
                    "version": "8359842"
                },
                "role": {
                    "id": "693",
                    "term": "referencedIn",
                    "uri": "http://socialarchive.iath.virginia.edu/control/term#referencedIn",
                    "type": "document_role"
                },
                "operation": "insert"
            }
        ]
    }
}
```
If the command succeeds, we will receive a success message and a new updated mini-Constellation with the IDs and Version numbers filled out.  Also notice that the Constellation's version number has increased to match.
```json
{
    "constellation": {
        "dataType": "Constellation",
        "status": "currently editing",
        "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
        "resourceRelations": [
            {
                "dataType": "ResourceRelation",
                "resource": {
                    "dataType": "Resource",
                    "id": "11612940",
                    "version": "8359842"
                },
                "role": {
                    "id": "693",
                    "term": "referencedIn",
                    "uri": "http://socialarchive.iath.virginia.edu/control/term#referencedIn",
                    "type": "document_role"
                },
                "id": "83119073",
                "version": "11254456",
                "operation": "insert"
            }
        ],
        "id": 50447072,
        "version": "11254456"
    },
    "result": "success",
    "timing": 762.66
}
```

### Publish Sherlock Holmes

To make our new changes public, and remove the Constellation from our editing queue (that is, "check it back in"), we will need to publish it.  *If you don't have publishing privileges, you will need to send the Constellation for Review.  That can most easily be done from the Dashboard online, but we will describe the API calls in another tutorial.*

In order to publish, we may make use of the response from the update command above.  We can reuse that exact Constellation object which has the correct, current, id and version.  We must PUT the following command to the REST API to initiate the publish:
```json
{
    "command": "publish_constellation",
    "user": { "...": "YOUR API INFO" },
    "constellation": {
        "dataType": "Constellation",
        "status": "currently editing",
        "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
        "resourceRelations": [
            {
                "dataType": "ResourceRelation",
                "resource": {
                    "dataType": "Resource",
                    "id": "11612940",
                    "version": "8359842"
                },
                "role": {
                    "id": "693",
                    "term": "referencedIn",
                    "uri": "http://socialarchive.iath.virginia.edu/control/term#referencedIn",
                    "type": "document_role"
                },
                "id": "83119073",
                "version": "11254456",
                "operation": "insert"
            }
        ],
        "id": 50447072,
        "version": "11254456"
    }
}
```

If the publish succeeds, we will receive a JSON response with "result" set to "success" with another copy of the mini-Constellation.  Everything will look the same, except the version number will be higher.  In this example, we can see that the version number was only incremented by 1 to 11254457.  *In cases of heavy server activity, the version numbers will not be sequential.*
```json
{
    "constellation": {
        "dataType": "Constellation",
        "status": "currently editing",
        "ark": "http://n2t.net/ark:/99166/w6nf1wh2",
        "resourceRelations": [
            {
                "dataType": "ResourceRelation",
                "resource": {
                    "dataType": "Resource",
                    "id": "11612940",
                    "version": "8359842"
                },
                "role": {
                    "id": "693",
                    "term": "referencedIn",
                    "uri": "http://socialarchive.iath.virginia.edu/control/term#referencedIn",
                    "type": "document_role"
                },
                "id": "83119073",
                "version": "11254456",
                "operation": "insert"
            }
        ],
        "id": 50447072,
        "version": "11254457"
    },
    "result": "success",
    "timing": 4605.35
}
```

We can verify our addition by viewing the Constellation online at http://snac-dev.iath.virginia.edu/view/50447072.
