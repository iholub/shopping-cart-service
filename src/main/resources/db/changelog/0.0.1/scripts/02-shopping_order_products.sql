create table shopping_order_products
(
    shopping_order_id bigint      not null,
    product_id        varchar(50) not null,
    product_order     integer     not null,
    primary key (shopping_order_id, product_order),
    foreign key (shopping_order_id) references shopping_order (id)
)
