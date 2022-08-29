#@namespace scala di.labs.service
include "UserInfoData.thrift"

service TUser {
    UserInfoData.TUserDetail getUserById(1:required string id)
    UserInfoData.TUserDetail addUser(1:required UserInfoData.TUserDetail userInfo)
    list<UserInfoData.TUserDetail> getUserByName(1:required string name)
}
