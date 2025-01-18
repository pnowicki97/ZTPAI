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
<form id="data" action="/addExpanse" method="POST"> </form>
<form id="get_back" action="/desktopGroupPage" method="POST"></form>

    <div class="container flex-column-center-center">

        <label><?= $group[0]->getName(); ?></label>

        <template id="expanse-template">
            <div class="element">
                <label></label>
                <paidBy></paidBy>
                <amount></amount>
            </div>
        </template>


        <div class="loginForm flex-column-center-center">
            <input form="data" type="text" name="name" placeholder="name"></input>
            <input form="data" type="text" name="amount" placeholder="amount"></input>
            <label>Paid by</label>
            <select form="data" name="paidby" placeholder="paid by">
            <?php foreach($items as $item): ?>
                <option form="data" value=<?= $item->getId(); ?>><?= $item->getName(); ?></option>
                <?php endforeach; ?>
            </select>
        </div>

    </div>
    <div class="homeButtons flex-row-center-center">

            <button type="submit" name=<?= $group[0]->getId(); ?> form="get_back">Back</button>
            <button type="submit" form="data">Add</button>
            <input name="groupId" hidden value=<?= $group[0]->getId(); ?> form="data"></input>
    </div>

</body>
</html>