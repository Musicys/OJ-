/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Commentson } from './Commentson';
import type { OrderItem } from './OrderItem';
export type Page_Commentson_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: number;
    records?: Array<Commentson>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};

