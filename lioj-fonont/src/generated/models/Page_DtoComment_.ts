/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DtoComment } from './DtoComment';
import type { OrderItem } from './OrderItem';
export type Page_DtoComment_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: number;
    records?: Array<DtoComment>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};

