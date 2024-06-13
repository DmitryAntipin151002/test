<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Projects Management</title>
    <link rel="stylesheet" type="text/css" href="resources/css/projectUsers.css">
</head>
<body>
<h1>Projects Management</h1>

<div class="form-container">
    <h2>Project List</h2>
    <table>
        <tr>
            <th>Project ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td>${project.id}</td>
                <td>${project.name}</td>
                <td>${project.description}</td>
                <td>
                    <!-- Update Project Form -->
                    <form action="ProjectServlet" method="post">
                        <input type="hidden" name="_method" value="PUT">
                        <input type="hidden" name="id" value="${project.id}">
                        <input type="text" name="name" value="${project.name}" required>
                        <input type="text" name="description" value="${project.description}" required>
                        <input type="submit" value="Update">
                    </form>
                    <!-- Delete Project Form -->
                    <form action="ProjectServlet" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="id" value="${project.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="form-container">
    <h2>Create Project</h2>
    <form action="ProjectServlet" method="post">
        <div>
            <input type="text" name="name" placeholder="Project Name" required>
        </div>
        <div>
            <input type="text" name="description" placeholder="Project Description" required>
        </div>
        <input type="submit" value="Create">
    </form>
</div>

</body>
</html>
