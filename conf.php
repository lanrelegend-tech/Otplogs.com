<?php

// Database
define('DB_HOST', 'localhost');
define('DB_NAME', 'datawhwg_boostpoint');
define('DB_USER', 'datawhwg_boostpoint');
define('DB_PASS', 'datawhwg_boostpoint');


// Contact Information
$whatsapp_number = '2348012345678';

$whatsapp_url =
    'https://wa.me/' .
    preg_replace('/[^0-9+]/', '', $whatsapp_number);

$telegram_url = '2348012345678';

$support_email = 'support@boostpoint.com.ng';

$site_name = 'BoostPoint';

$address = 'Lagos, Nigeria';

$phone = '+1234567890';


// PaymentPoint API
define(
    'PAYMENTPOINT_API_KEY',
    '49129a8f66274001feed2223c89f5f3511516450'
);

define(
    'PAYMENTPOINT_API_SECRET',
    '00ddb27d62c8cfadbce99387d31ddc403df98f1961a5d85c641db5be510ec20d9c5c59b51950c3cee7d91acc62c1ef92ff0a25c82a4b00990483c0bc'
);

define(
    'PAYMENTPOINT_BUSINESS_ID',
    'c728cae6583940d5fe67556f7993772c5393516d'
);


// BenOTP API
define(
    'BENOTP_API_KEY',
    'ujg7z1ydvt6jhfvkoi1llrtbdny263e3'
);

define(
    'BENOTP_BASE_URL',
    'https://api.benotp.com/stubs/handler.php'
);

define(
    'BENOTP_API_URL',
    'https://api.benotp.com/stubs/handler.php'
);


// USA API
define(
    'BENOTP_USA_API_URL',
    'https://api.benotp.com/stubs/handler_api.php'
);


// Profit Margin
define('PROFIT_MARGIN_PERCENT', 100);


// SmExploits API
define(
    'SMEXPLOITS_API_URL',
    'https://smexploits.com/api/v2'
);

define(
    'SMEXPLOITS_API_KEY',
    '3cba7528c094db3a7b4fb8f53e1af717'
);
?>