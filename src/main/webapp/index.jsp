<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <link rel="stylesheet" href="resources/css/index.css">
</head>
<body>
<div class="container">
    <h1>Welcome to Database Interaction</h1>
    <p>Please select the database you want to interact with:</p>
    <div class="buttons">
        <form action="UserServlet" method="get">
            <button type="submit" class="user-btn">Users</button>
        </form>
        <form action="TaskServlet" method="get">
            <button type="submit" class="task-btn">Tasks</button>
        </form>
        <form action="ProjectUserServlet" method="get">
            <button type="submit" class="project-user-btn">Project Users</button>
        </form>
        <form action="ProjectServlet" method="get">
            <button type="submit" class="project-btn">Projects</button>
        </form>
    </div>
</div>
</body>
</html>
