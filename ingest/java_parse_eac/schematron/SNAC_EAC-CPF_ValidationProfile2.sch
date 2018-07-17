<schema xmlns="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2" schemaVersion="ISO19757-3">
    <title>SNAC EAC-CPF Profile Instance Validation</title>
    <ns prefix="cpf" uri="urn:isbn:1-931666-33-4"></ns>
    <ns prefix="xlink" uri="http://www.w3.org/1999/xlink"></ns>
    
    
    
    
    
    
    <pattern>
        <rule context="*">
            
            <assert test="not(@xml:base)">The XML:BASE attribute is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:objectBinWrap)">The OBJECTBINWRAP element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:alternativeSet)">The ALTERNATIVESET element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:multipleIdentities)">The MUTLIPLEIDNETITIES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:control">
            <assert test="not(cpf:publicationStatus)">The PUBLICATIONSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:localTypeDeclaration)">The LOCALTYPEDECLARATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:localControl)">The LOCALCONTROL element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:otherRecordId">
            <assert test="@localType">The LOCALTYPE attribute on the OTHERRECORDID element is required by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:maintenanceAgency">
            <assert test="not(cpf:agencyCode)">The AGENCYCODE element in the MAINTENANCEAGENCY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:otherAgencyCode)">The OTHERAGENCYCODE element in the MAINTENANCEAGENCY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the MAINTENANCEAGENCY element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:languageDeclaration">
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LANGUAGEDECLARATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:languageDeclaration/cpf:language">
            <assert test="text()">A text value inside the LANGUAGEDECLARATION/LANGUAGE element is required by the SNAC EAC-CPF profile. </assert>
        </rule>
        <rule context="cpf:languageDeclaration/cpf:script">
            
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:conventionDeclaration">
            <assert test="not(cpf:abbreviation)">The ABBREVIATION element in the CONVENTIONDECLARATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the CONVENTIONDECLARATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:maintenanceEvent">
            
            
        </rule>
        <rule context="cpf:eventDateTime">
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:source">
            <assert test="not(@xlink:title)">The XLINK:TITLE attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:actuate)">The XLINK:ACTUATE attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:show)">The XLINK:SHOW attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:role)">The XLINK:ROLE attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:arcrole)">The XLINK:ARCROLE attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:sourceEntry)">The SOURCEENTRY element in the SOURCE element is not supported by the SNAC EAC-CPF profile.</assert>
            
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:cpfDescription">
            <assert test="not(cpf:alternativeSet)">The ALTERNATIVESET element in the CPFDESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:identity">
            <assert test="not(@localType)">The LOCALTYPE attribue on the IDENTITY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@identityType)">The IDENTITYTYPE attribute on the IDENTITY element is not supported by the SNAC EAC-CPF profile</assert>
            
            <assert test="not(cpf:nameEntryParallel)">The NAMEENTRYPARALLEL element in the IDENTITY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the IDENTITY element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:nameEntry">
            
            <assert test="not(@transliteration)">The TRANSLITERATION attribute on the NAMEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@localType)">The LOCALTYPE attribute on the NAMEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:preferredForm)">The PREFERREDFORM attribute in the NAMEENTRY lement is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:part">
            <assert test="not(@localType)">The LOCALTYPE attribute on the PART element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:description">
            <assert test="not(cpf:places)">The PLACES element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:localDescriptions)">The LOCALDESCRIPTIONS element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:legalStatuses)">The LEGALSTATUSES element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:functions)">The FUNCTIONS element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:languagesUsed)">The LANGUAGESUSED element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:occupations)">The OCCUPATIONS element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:mandates)">The MANDATES element in the DESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:existDates">
            <assert test="not(@localType)">The LOCALTYPE attribute in the EXISTDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:places">
            <assert test="not(@localType)">The LOCALTYPE attribute on the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The LIST element in the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the PLACES element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:place">
            
            <assert test="not(cpf:address)">The ADDRESS element in the PLACE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the PLACE element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:dateSet)">The DATESET element in the PLACE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the PLACE element is not supported by the SNAC EAC-CPF profile.</assert>
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:localDescriptions">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The LIST element in the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the LOCALDESCRIPTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:localDescription">
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the LOCALDESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element in the LOCALDESCRIPTION element is not xxx</assert>
            <assert test="not(cpf:dateSet)">The DATESET element in the LOCALDESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the LOCALDESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LOCALDESCRIPTION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:languagesUsed">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LANGUAGESUSED element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LANGUAGESUSED element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:languageUsed">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LANGUAGEUSED element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LANGUAGEUSED element is not supported by the SNAC EAC-CPF profile.</assert>
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:occupations">
            <assert test="not(@localType)">The LOCALTYPE attribute on the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The list element in the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the OCCUPATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:occupation">
            <assert test="not(@localType)">The LOCALTYPE attribute on the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the OCCUPATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:legalstatuses">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The list element in the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the LEGALSTATUSES element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:legalstatus">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the LEGALSTATUS element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:functions">
            <assert test="not(@localType)">The LOCALTYPE attribute on the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The list element in the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the FUNCTIONS element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:function">
            <assert test="not(@localType)">The LOCALTYPE attribute on the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the FUNCTION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:mandates">
            <assert test="not(@localType)">The LOCALTYPE attribute on the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:descriptiveNote)">The DESCRIPTIVENOTE element in the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:p)">The P element in the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:list)">The list element in the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the MANDATES element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:mandate">
            <assert test="not(@localType)">The LOCALTYPE attribute on the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element in the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element in the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element in the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element in the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:citation)">The CITATION element in the MANDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:structureOrGenealogy">
            <assert test="not(@localType)">The LOCALTYPE attribute on the STRUCTUREORGENEALOGY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the STRUCTUREORGENEALOGY element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:generalContext">
            <assert test="not(@localType)">The LOCALTYPE attribute on the GENERALCONTEXT element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the GENERALCONTEXT element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:biogHist">
            <assert test="not(@localType)">The LOCALTYPE attribute on the BIOGHIST element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:abstract)">The ABSTRACT element in the BIOGHIST element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:chronList)">The CHRONLIST element in the BIOGHIST element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:outline)">The OUTLINE element in the BIOGHIST element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:relations">
            <assert test="not(cpf:functionRelation)">The FUNCTIONRELATION element in the RELATIONS element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:cpfRelation">
            
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:arcrole">The XLINK:ARCROLE attribute on the CPFRELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:role">The XLINK:ROLE attribute on the CPFRELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:title)">The XLINK:TITLE attribute on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:actuate)">The XLINK:ACTUATE attribute on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:show)">The XLINK:SHOW attribute on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            
            <assert test="not(cpf:dateSet)">The DATESET element on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element on the CPFRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:resourceRelation">
            
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:arcrole">The XLINK:ARCROLE attribute on the RESOURCERELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:role">The XLINK:ROLE attribute on the RESOURCERELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:title)">The XLINK:TITLE attribute on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:actuate)">The XLINK:ACTUATE attribute on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:show)">The XLINK:SHOW attribute on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element on the RESOURCERELATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:functionRelation">
            <assert test="not(@functionRelationType)">The FUNCTIONRELATIONTYPE attribute on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:arcrole">The XLINK:ARCROLE attribute on the FUNCTIONRELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="@xlink:role">The XLINK:ROLE attribute on the FUNCTIONRELATION element is required by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:title)">The XLINK:TITLE attribute on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:actuate)">The XLINK:ACTUATE attribute on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:show)">The XLINK:SHOW attribute on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSingle)">The DATESINGLE element on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateRange)">The DATERANGE element on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:dateSet)">The DATESET element on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(cpf:placeEntry)">The PLACEENTRY element on the FUNCTIONRELATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:relationEntry">
            
            <assert test="not(@scriptCode)">The SCRIPTCODE attribute on the RELATIONENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@transliteration)">The TRANSLITERATION attribute on the RELATIONENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:objectXMLWrap">
            
        </rule>
    </pattern>
    
    
    <pattern>
        <rule context="cpf:dateSingle">
            <assert test="not(@localType)">The LOCALTYPE attribute on the DATESINGLE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@notBefore)">The NOTBEFORE attribute on the DATESINGLE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@notAfter)">The NOTAFTER attribute on the DATESINGLE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@standardDate">The STANDARDDATE attribute on the DATESINGLE element is required by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:dateRange">
            <assert test="not(@localType)">The LOCALTYPE attribute on the DATERANGE element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:fromDate">
            <assert test="not(@notBefore)">The NOTBEFORE attribute on the FROMDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@notAfter)">The NOTAFTER attribute on the FROMDATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@standardDate">The STANDARDDATE attribute on the FROMDATE element is required by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:toDate">
            <assert test="not(@notBefore)">The NOTBEFORE attribute on the TODATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@notAfter)">The NOTAFTER attribute on the TODATE element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="@standardDate">The STANDARDDATE attribute on the TODATE element is required by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:dateSet">
            <assert test="not(@localType)">The LOCALTYPE attribute on the DATESET element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    
    <pattern>
        <rule context="cpf:term">
            <assert test="not(@scriptCode)">The SCRIPTCODE attribute on the TERM element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@transliteration)">The TRANSLITERATION attribute on the TERM element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the TERM element is not supported by the SNAC EAC-CPF profile.</assert>
            
        </rule>
        <rule context="cpf:citation">
            <assert test="not(@xlink:type)">The XLINK:TYPE attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:href)">The XLINK:HREF attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:title)">The XLINK:TITLE attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:actuate)">The XLINK:ACTUATE attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:show)">The XLINK:SHOW attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:role)">The XLINK:ROLE attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@xlink:arcrole)">The XLINK:ARCROLE attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@lastDateTimeVerified)">The LASTDATETIMEVERIFIED attribute on the CITATION element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:descriptiveNote">
            
        </rule>
    </pattern>
    
    
    <pattern>
        <rule context="cpf:p">
            
        </rule>
        <rule context="cpf:outline">
            <assert test="not(@localType)">The LOCALTYPE attribute on the OUTLINE element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:level">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LEVEL element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:item">
            <assert test="not(@localType)">The LOCALTYPE attribute on the ITEM element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:list">
            <assert test="not(@localType)">The LOCALTYPE attribute on the LIST element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    
    <pattern>
        <rule context="cpf:span">
            <assert test="@localType or @style">Either the LOCALTYPE attribute or the STYLE attribute is required on the SPAN element by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:chronList">
            <assert test="not(@localType)">The LOCALTYPE attribute on the CHRONLIST element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
        <rule context="cpf:event">
            <assert test="not(@localType)">The LOCALTYPE attribute on the EVENT element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
    
    <pattern>
        <rule context="cpf:placeEntry">
            <assert test="not(@scriptCode)">The SCRIPTCODE attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@transliteration)">The TRANSLITERATION attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            
            
            <assert test="not(@accuracy)">The ACCURACY attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@latitude)">The LATTITUDE attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@longitude)">The LONGITUDE attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
            <assert test="not(@altitude)">The ALTITUDE attribute on the PLACEENTRY element is not supported by the SNAC EAC-CPF profile.</assert>
        </rule>
    </pattern>
</schema>