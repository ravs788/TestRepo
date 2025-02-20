# Readme file for Encrypting secrets

## Initialize git-crypt for project

1. git-crypt init

## Create .gitattributes file to sepcify files to encrypt

2. Content as
   secrets.conf filter=git-crypt diff=git-crypt

## Create Key for encrypting

3. git-crypt export-key ./git-crypt-key-testRepo

## References

- [Git-Crypt Official Github](https://github.com/AGWA/git-crypt)
