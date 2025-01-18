<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="public/css/main.css">
    <title><?= $title; ?></title>
</head>

<body>
<form id="data" action="/addGroup" method="POST"> </form>
<form id="get_back" action="/desktopUserPage" method="POST"></form>

    <div class="container flex-column-center-center">

        <label>Add Group</label>

        <div class="container flex-row-center-center">

            <template id="expanse-template">
                <div class="element">
                    <label></label>
                    <paidBy></paidBy>
                    <amount></amount>
                </div>
            </template>

                <div class="loginForm flex-column-center-center">
                <input form="data" type="text" name="name" placeholder="name"></input>
                <input form="data" type="text" name="photoUrl" placeholder="photo"></input>
                </div>

            <div class="groups flex-column-center-center">
                <?php foreach($items as $item): ?>
                <div class="element flex-row-center-center">
                    <div class="flex-row-center-center">
                        <label form="data"><?= $item->getName(); ?></label>
                    </div>
                    <input form="data" type="checkbox" name="checkbox[]" value=<?= $item->getId(); ?>></input>
                </div>
                <?php endforeach; ?>
            </div>
        </div>

    </div>
    <div class="homeButtons flex-row-center-center">

            <button type="submit" form="get_back">Back</button>
            <button type="submit" form="data">Add</button>
    </div>

</body>
</html>