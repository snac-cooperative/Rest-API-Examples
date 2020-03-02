# Ruby script to read a CSV and send API requests to update SNAC resources.
# To insert new resources instead of updating, leave resource id blank and change operation to "insert".


require "httparty"

endpoint = "https://snac-dev.iath.virginia.edu/api/"
api_key = "[INSERT API KEY]"

CSV.foreach("./resources_for_update.csv", headers: true, header_converters: :symbol) do |row|
# CSV with columns: id, type, title, display_entry, abstract, link, extent, date, repo_ic_id
    request = {
        "command": "insert_resource",
        "resource": {
            "dataType": "Resource",
            "documentType": {
                "id": row[:type]
            },
            "id": row[:id],
            "title": row[:title],
            "displayEntry": row[:display_entry],
            "abstract": row[:abstract],
            "link": row[:link],
            "extent": row[:extent],
            "date": row[:date],
            "repository": {
              "dataType": "Constellation",
              "id": row[:repo_ic_id]                     #SNAC ID of the resource's holding repository
            },
            "operation": "update"
        },
        "apikey": "#{api_key}"
    }

    response = HTTParty.put(endpoint, :body => request.to_json, :verify => false)

    puts "Updated \n Resource id: #{response["resource"]["id"]} \n Resource title: #{response["resource"]["title"]}"
end
