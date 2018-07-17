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
The data type *is* case sensitive, which has caused some trouble in the past.  We're working on either removing that or clearing up the case sensitivity issue.  We used the ArchivalResource type for the document and provided the link, title, abstract, and extent.   The display entry is a human-readable and distinguishable field, so we added the author's name to the beginning.  The repository link is a minimalistic Constellation with the ID of the holding institution we found above.  Lastly, we include an "operation" field that will tell the server we want to insert this resource (rather than update it).

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

The REST API will return the full written Resource object with IDs and Version numbers from the SNAC server.  Hang on to this object, since we'll use it when we update a Constellation later to connect with this resource.
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
One thing to notice in this response is that the system filled out the repository information, giving us the preferred name entry, entity type, and ark of the holding repository.  If there were place information, including latitude, longitude, and address, that would have been included as well.  At the bottom of our Resource object, we can see it now has an id: `11612940`.  We can now view our resource online at `http://snac-dev.iath.virginia.edu/vocab_administrator/resource/11612940`.


## Adding the Resource Relation
