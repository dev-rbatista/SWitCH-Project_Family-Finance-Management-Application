import {
    // Insert action's names you want to import
    // ACTION_X
    // ACTION_Y
    FETCH_PROFILE_STARTED,
    FETCH_PROFILE_SUCCESS,
    FETCH_PROFILE_FAILURE,
    UPDATE_NAME,
    CHANGE_USER,
    LOGOUT, CHANGE_VIEW,
} from './Actions'

function reducer(state, action) {
    switch (action.type){
        /*
        case ACTION_HERE:
            return {
                ...state,
                {data object from AppProvider}: {
                    {variable 1}: x,
                    {variable 2}: y,
                }
            }

         */
        case FETCH_PROFILE_STARTED:
            return {
                ...state,
                profile: {
                    loading: true,
                    error: null,
                    data: []
                }
            }
        case FETCH_PROFILE_SUCCESS:
            return {
                ...state,
                profile: {
                    loading: false,
                    error: null,
                    data: action.payload.data
                }
            }
        case FETCH_PROFILE_FAILURE:
            return {
                ...state,
                profile: {
                    loading: false,
                    error: action.payload.error,
                    data: [],
                }
            }

        case UPDATE_NAME:
            let teste = action.payload.data;
            if ( teste === '3'){
                teste = 'vai po crlh';
            }
            return {
                ...state,
                name: {
                    loading: false,
                    error: null,
                    data: teste,
                }
            }

        case CHANGE_USER:
            return {
                ...state,
                loggeduser: action.payload.data
            }

        case LOGOUT:
            return {
                ...state,
                loggedUser: {
                    id: null,
                    sm: false,
                    fa: false,
                }
            }

        case CHANGE_VIEW:
            return {
                ...state,
                mainView: action.payload.mainView,
            }

        default:
            return state;
    }
}

export default reducer;