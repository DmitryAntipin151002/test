<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Management</title>
    <link rel="stylesheet" type="text/css" href="resources/css/task.css">
</head>
<body>
<h1>Task Management</h1>

<div class="form-container">
    <h2>Tasks List</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>User ID</th>
            <th>Action</th>
        </tr>
        <c:forEach var="task" items="${tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.title}</td>
                <td>${task.description}</td>
                <td>${task.userId}</td>
                <td>
                    <!-- Edit Task Form -->
                    <form action="TaskServlet" method="post">
                        <input type="hidden" name="_method" value="PUT">
                        <input type="hidden" name="id" value="${task.id}">
                        <div>
                            <input type="text" name="title" value="${task.title}" placeholder="Title" required>
                        </div>
                        <div>
                            <input type="text" name="description" value="${task.description}" placeholder="Description" required>
                        </div>
                        <div>
                            <input type="number" name="userId" value="${task.userId}" placeholder="User ID" required>
                        </div>
                        <input type="submit" value="Edit">
                    </form>
                    <!-- Delete Task Form -->
                    <form action="TaskServlet" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="id" value="${task.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="form-container">
    <h2>Create Task</h2>
    <form action="TaskServlet" method="post">
        <div>
            <input type="text" name="title" placeholder="Title" required>
        </div>
        <div>
            <input type="text" name="description" placeholder="Description" required>
        </div>
        <div>
            <input type="number" name="userId" placeholder="User ID" required>
        </div>
        <
        <input type="submit" value="Create">
    </form>
</div>

</body>
</html>