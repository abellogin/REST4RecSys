<#-- @ftlvariable name="" type="IndexView" -->

<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1>Index</h1>

        <p>Choose what you want to do</p>

        <ul>
            <li>
                <a href="../local_interface/get/users">See users in the system</a>
            </li>
            <li>
                <a href="../local_interface/add/user">Add a user to the system</a>
            </li>
            <li>
                <a href="../local_interface/get/items">See items in the system</a>
            </li>
            <li>
                <a href="../local_interface/add/item">Add an item to the system</a>
            </li>
            <li>
                <a href="../local_interface/add/event">Add an event in the system</a>
            </li>	
            <li>
                <a href="../local_interface/get/events">See events in the system</a>
            </li>	
            <li>
                <a href="../local_interface/get/stats">See statistics from the system</a>
            </li>	
            <li>
                <form action="../local_interface/upload" method="post" enctype="multipart/form-data">
                    <p>Select file: <input type="file" name="data_file" id="data_file"/></p>
                    <input type="submit" value="Submit a dataset"/>
                </form>
            </li>	
        </ul>

    </body>
</html>
