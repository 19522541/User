#@namespace scala di.labs.service
include "UserInfoData.thrift"

service TUser {
    UserInfoData.TResult getUserById(1:required string id)
    UserInfoData.TResult addUser(1:required UserInfoData.TUserDetail userInfo)
    UserInfoData.TResult getUserByName(1:required string name)
}
