/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DtoRequested } from './DtoRequested';
import type { OrderItem } from './OrderItem';
export type Page_DtoRequested_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: number;
    records?: Array<DtoRequested>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};

