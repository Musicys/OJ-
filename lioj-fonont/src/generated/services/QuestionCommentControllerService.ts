/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from '../models/BaseResponse_boolean_';
import type { BaseResponse_Page_Commentson_ } from '../models/BaseResponse_Page_Commentson_';
import type { BaseResponse_Page_DtoComment_ } from '../models/BaseResponse_Page_DtoComment_';
import type { BaseResponse_Page_DtoRequested_ } from '../models/BaseResponse_Page_DtoRequested_';
import type { BaseResponse_string_ } from '../models/BaseResponse_string_';
import type { DeleteRequest } from '../models/DeleteRequest';
import type { PostAddComment } from '../models/PostAddComment';
import type { PostListComment } from '../models/PostListComment';
import type { Requested } from '../models/Requested';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class QuestionCommentControllerService {
    /**
     * editPost
     * @param postaddRequest postaddRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static editPostUsingPost1(
        postaddRequest: PostAddComment,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/comment/add',
            body: postaddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * AddRequested
     * @param commentid Commentid
     * @returns BaseResponse_string_ OK
     * @throws ApiError
     */
    public static addRequestedUsingGet(
        commentid?: number,
    ): CancelablePromise<BaseResponse_string_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/comment/add/requested ',
            query: {
                'Commentid': commentid,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * delLike
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @throws ApiError
     */
    public static delLikeUsingGet(
        id?: number,
    ): CancelablePromise<BaseResponse_boolean_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/comment/del/liek',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deletePost
     * @param deleteRequest deleteRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static deletePostUsingPost1(
        deleteRequest: DeleteRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/comment/delete',
            body: deleteRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * addLike
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @throws ApiError
     */
    public static addLikeUsingGet(
        id?: number,
    ): CancelablePromise<BaseResponse_boolean_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/comment/get/like',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listPostCmentByPage
     * @param postQueryRequest postQueryRequest
     * @returns BaseResponse_Page_DtoComment_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listPostCmentByPageUsingPost(
        postQueryRequest: PostListComment,
    ): CancelablePromise<BaseResponse_Page_DtoComment_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/comment/list/page',
            body: postQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listPostRequestedByPage
     * @param requested requested
     * @returns BaseResponse_Page_DtoRequested_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listPostRequestedByPageUsingPost(
        requested: Requested,
    ): CancelablePromise<BaseResponse_Page_DtoRequested_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/comment/list/page/requested',
            body: requested,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listPostComentByPageSon
     * @param postQueryRequest postQueryRequest
     * @returns BaseResponse_Page_Commentson_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listPostComentByPageSonUsingPost(
        postQueryRequest: PostListComment,
    ): CancelablePromise<BaseResponse_Page_Commentson_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/comment/list/page/son',
            body: postQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
