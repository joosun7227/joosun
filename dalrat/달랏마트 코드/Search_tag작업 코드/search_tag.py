def update_search_tag(name, additional_attributes):
    # 영어 제외한 태국어와 한국어 추출
    cleaned_name = ''.join(filter(lambda x: '가' <= x <= '힣' or 'ก' <= x <= '๛', name))
    # 중복 제거
    unique_chars = ''.join(sorted(set(cleaned_name), key=cleaned_name.index))
    # 세그먼트 생성
    segments = [unique_chars[i:i+n] for n in range(1, 5) for i in range(len(unique_chars) - n + 1)]
    new_search_tag = ' '.join(segments + [unique_chars])
    
    # 기존 search_tag 교체
    new_attributes = additional_attributes
    if 'search_tag=' in additional_attributes:
        pre, post = additional_attributes.split('search_tag=', 1)
        post = ','.join(post.split(',')[1:]) # 첫 번째 comma 이후의 내용만 유지
        new_attributes = pre + f'search_tag={new_search_tag},' + post
    else:
        new_attributes += f',search_tag={new_search_tag}'
        
    return new_attributes

# 모든 상품에 대해 search_tag 업데이트
data['additional_attributes'] = data.apply(lambda x: update_search_tag(x['name'], x['additional_attributes']), axis=1)

# 수정된 파일 저장
output_file_path = 'updated_export_catalog_product.csv'
data.to_csv(output_file_path, index=False, encoding='utf-8')

output_file_path