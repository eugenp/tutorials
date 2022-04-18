<head>
    <meta charset="utf-8">
    <title>SQL Inject Sandbox</title>
    <meta name="description" content="SQL Inject TutoriaP">
    <meta name="author" content="Guilherme Chehab">
</head>
<script>
    function copyEvent(id) {
        var t = document.getElementById(id);
        window.getSelection().selectAllChildren(t);
        var d = document.getElementById('cmd');
        d.value = t.innerHTML;
    }
</script>
<?php
    $db_spec = 'mysql:host=db;port=3306;dbname=dbapp';
    $db_user = 'devuser';
    $db_pass = 'devpass';
    if ($_POST) {  
        $txtUsername = $_POST["Username"];
	    $txtPassword = $_POST["Password"];
            $hashPassword= md5($txtPassword);
	    $dbh = new PDO($db_spec, $db_user, $db_pass);
		$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	    try {
            if (isset($_POST['btnVulnerable'])) {
                $sql = "SELECT id,Username FROM users WHERE Password ='" . $hashPassword . "' AND Username ='" . $txtUsername . "'";
                $stmt = $dbh->query($sql);
            } else {
                $sql = 'SELECT id,Username FROM users WHERE Password = :bindPasswordHash AND Username = :bindUsernameTxt';
                $stmt =$dbh->prepare($sql);
                $stmt->bindParam('bindPasswordHash',$hashPassword);
                $stmt->bindParam('bindUsernameTxt',$txtUsername);
                $stmt->execute();
                // set the resulting array to associative
                $stmt->setFetchMode(PDO::FETCH_ASSOC);
            }
        } catch (PDOException $e) {
            print "Error!: " . $e->getMessage() . "<br/>";
            die();
        }
        $dbh = null;
    } else {
	    $txtUsername = "root";
	    $txtPassword = "root";
    }
?>
<body>
    <h1>SQL Inject Tutorial</h1>
    <div>
        <h3>Instructions</h3>
        <p>Use the <strong>Submit</strong> button to use the non-mitigated version of the SQL clause</P>
        <p>The <Strong>Submit (mitigated)</strong> will show the results of the mitigated SQL clause</p>
        <p>The password for the user <i>root</i> is also <i>root</i> -- yeah! Very bad practice, as letting SQL injection going through.</p>
        <p>First try a regular access with those credentials to see the query and its response.</p>
        <p>Now let's try some other clauses, replace the <i>username</i> with the following, and clik <strong>Submit</strong> to see what happens:</p>
        <table>
            <tr><td>1:</td><td><p id='cmd1'>' or 1=1 limit 1;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd1")'>Copy</button></td></tr>
            <tr><td>2:</td><td><p id='cmd2'>' or 1=1;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd2")'>Copy</button></td></tr>
            <tr><td>3:</td><td><p id='cmd3'>' or 1=1; select * from customers;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd3")'>Copy</button></td></tr>
            <tr><td>4:</td><td><p id='cmd4'>' or 1=1; insert into users(username,password) values('someuser','somehash');--  </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd4")'>Copy</button></td></tr>
            <tr><td>5:</td><td><p id='cmd5'>' or 1=1; delete from customers;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd5")'>Copy</button></td></tr>
            <tr><td>6:</td><td><p id='cmd6'>' or 1=1; drop table customers;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd6")'>Copy</button></td></tr>
            <tr><td>7:</td><td><p id='cmd7'>' or 1=1 union select id, password as username from users;-- </p></td><td><button style='margin-left: 16em;' type='button' onclick='copyEvent("cmd7")'>Copy</button></td></tr>
        </table>
    </div>
    <h3>Current data</h3>
    <div class=".db-table">
	<table>
        <tr>
            <td>
                <table style="border: 1px solid black">
                    <tr colspan=2>Users</tr>
                        <tr>
                            <th>Id</th>
                            <th>User</th>
                        </tr>
                        <?php
                        try {
                            $dbh = new PDO($db_spec, $db_user, $db_pass);
                            $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                            foreach ($dbh->query('SELECT * from users') as $row) {
                                $html = "<tr><td>${row['id']}</td><td>${row['username']}</td></tr>";
                                echo $html;
                            }
                        } catch (PDOException $e) {
                            print "Error!: " . $e->getMessage() . "<br/>";
                            die();
                        }
                        $dbh = null;
                        ?>
                    </table>
                </td>
                <td>
                    <table style="border: 1px solid black">
                        <tr colspan=2>Customers</tr>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>SSN</th>
                                <th>Credit Card</th>
                            </tr>
                            <?php
                            try {
                                $dbh = new PDO($db_spec, $db_user, $db_pass);
                                $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                                foreach ($dbh->query('SELECT * from customers') as $row) {
                                    $html = "<tr><td>${row['id']}</td><td>${row['name']}</td><td>${row['ssn']}</td><td>${row['ccard']}</td></tr>";
                                    echo $html;
                                }
                            } catch (PDOException $e) {
                                print "Error!: " . $e->getMessage() . "<br/>";
                                die();
                            }
                            $dbh = null;
                            ?>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div>
     <form action="index.php" method="post">

        <h2>LOGIN</h2>

        <?php if (isset($_GET['error'])) { ?>

            <p class="error"><?php echo $_GET['error']; ?></p>

        <?php } ?>

        <label>User Name</label>

	<input type="text" name="Username" placeholder="User Name" 
        value="<?php echo htmlspecialchars($txtUsername);?>" id="cmd"/>

        <label>Password</label>

	<input type="text" style="text-security:disc; -webkit-text-security:disc;" name="Password" 
        placeholder="Password" value="<?php echo htmlspecialchars($txtPassword);?>"/>

        <button type="submit" name="btnVulnerable">Submit</button>
        <button type="submit" name="btnMitigated">Submit (mitigated)</button>

     </form>
   </div>
   <div>
        <?php
	if ($_POST) {  
        $dbh = new PDO($db_spec, $db_user, $db_pass);
		$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                    echo "<h3>Resulting SQL clause</h3>";
                    echo "<p>${sql}</p>";
                    if (!isset($_POST['btnVulnerable'])) {
                        echo "<p>Binding Parameter bindPasswordHash=${hashPassword}</p>";
                        echo "<p>Binding Parameter bindUsernametTxt=${txtUsername}</p>";
                    }
	    try {
            do {
                $result = $stmt->fetchALL(PDO::FETCH_ASSOC);
                if (count($result)) {
                    echo "<table style='border: 1px solid black'><th colspan=".Count($row)."}>Results</th><tr>";
                        foreach ($result[0] as $col=>$val) {
                                echo "<th>${col}</th>";
                        }
                        foreach ($result as $row) {
                            echo "<tr>";
                            foreach ($row as $item) {
                            $html = "<td>${item}</td>";
                            echo $html;
                        }
                                echo "</tr>";
                    }
                    echo "</table>";
                }
                echo "<p>Result is ".count($result)." rows</p>";
            } while ($stmt->nextRowset());
        } catch (PDOException $e) {
            print "Error!: " . $e->getMessage() . "<br/>";
            die();
        }
	    $dbh = null;
	}
	?>
   </div>
</body>
</html>
