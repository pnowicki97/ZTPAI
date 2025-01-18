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
    <div class="container flex-column-center-center">

        <template id="expanse-template">
            <div class="element">
                <label></label>
                <paidBy></paidBy>
                <amount></amount>
            </div>
        </template>

        <label><?= $group[0]->getName(); ?></label>

        <div class="groups flex-row-center-center">
            <?php foreach($items as $item): ?>
            <div class="element flex-column-center-center">
                <div class="flex-row-center-center">
                    <label><?= $item->getName(); ?></label>
                    <label><?= $item->getAmount(); ?></label>
                </div>
                <label class="paidBy"> Paid by <?= $item->getPaidBy(); ?></label>
            </div>
            <?php endforeach; ?>
        </div>


    </div>
    <div class="homeButtons flex-row-center-center">
        <form action="/desktopUserPage" class = "homeButtons">
            <button type="submit">Back</button>
        </form>
        <form action="/desktopAddExpanse" method="POST" class = "homeButtons">
            <button type="submit" name=<?= $group[0]->getId(); ?>>Add Expanse</button>
        </form>
    </div>
</body>
</html>